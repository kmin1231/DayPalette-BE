// posts/service/PostCreateService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.posts.dto.PostCreateRequest;
import com.khu.cloud.diary.posts.dto.PostCreateResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.posts.repository.PostRepository;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil; 
import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class PostCreateService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    
    @Autowired
    private HttpServletRequest request;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest requestDto) {

        // Authorization header에서 사용자 정보 추출
        String email = extractEmailFromJwt();

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        // post 저장
        Post post = Post.builder()
                .user(member)
                .date(requestDto.getDate())
                .diaryText(requestDto.getDiaryText())
                .imageUrl(requestDto.getImageUrl())
                .build();

        Post savedPost = postRepository.save(post);

        // response
        return new PostCreateResponse(
                savedPost.getPostId(),
                savedPost.getDate(),
                savedPost.getDiaryText(),
                savedPost.getImageUrl(),
                savedPost.getCreatedAt()
        );
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