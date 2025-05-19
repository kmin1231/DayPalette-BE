// posts/service/PostCreateService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.posts.dto.PostCreateRequest;
import com.khu.cloud.diary.posts.dto.PostCreateResponse;
import com.khu.cloud.diary.posts.dto.GenerateImageResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.repository.PostRepository;
import com.khu.cloud.diary.posts.util.S3UploadService;
import com.khu.cloud.diary.posts.util.FileNameGenerator;
import com.khu.cloud.diary.member.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class PostCreateService {

    private final PostRepository postRepository;
    private final S3UploadService s3UploadService;
    private final JwtUtil jwtUtil;
    
    @Autowired
    private WebClient webClient;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest requestDto) {

        // Authorization header에서 사용자 정보 추출
        String email = extractEmailFromJwt();

        // FastAPI 서버에 이미지 생성 요청
        // GenerateImageResponse imageResponse = generateImageFromAI(requestDto.getDiaryText(), requestDto.getEmoji());
        GenerateImageResponse imageResponse = generateImageFromAI(requestDto.getDiaryText());

        // S3에 이미지 업로드 -> URL return
        String fileName = FileNameGenerator.generateFileName(email);
        String imageUrl = s3UploadService.uploadImageBytes(imageResponse.getDiaryImage(), email, fileName, "image/png");

        // post 저장
        Post post = Post.builder()
                .diaryText(requestDto.getDiaryText())
                // .emoji(requestDto.getEmoji())
                .imageUrl(imageUrl)
                .build();

        Post savedPost = postRepository.save(post);

        // response
        return new PostCreateResponse(
                savedPost.getPostId(),
                savedPost.getDiaryText(),
                // savedPost.getEmoji(),
                savedPost.getImageUrl(),
                savedPost.getCreatedAt()
        );
    }

    @Value("${ai.server.url}")
    private String aiServerUrl;

    // FastAPI 서버에 이미지 생성 요청
    // private GenerateImageResponse generateImageFromAI(String diaryText, String emoji) {
    private GenerateImageResponse generateImageFromAI(String diaryText) {
        return webClient.post()
                .uri(aiServerUrl + "/generate")
                // .bodyValue(new PostCreateRequest(diaryText, emoji))
                .bodyValue(new PostCreateRequest(diaryText))
                .retrieve()
                .bodyToMono(byte[].class)
                .map(bytes -> new GenerateImageResponse(bytes))
                .block();
    }

    // Authorization header에서 사용자 정보(email) 추출
    private String extractEmailFromJwt() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return jwtUtil.extractEmail(token);
    }
}