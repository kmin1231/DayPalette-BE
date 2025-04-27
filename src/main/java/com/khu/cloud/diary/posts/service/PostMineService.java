// posts/service/PostMineService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.posts.dto.PostMineResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.repository.PostRepository;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMineService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @Transactional(readOnly = true)
    public ApiResponse<List<PostMineResponse>> getMyPosts() {
        String token = resolveTokenFromRequest();
        String email = jwtUtil.extractEmail(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        List<Post> posts = postRepository.findAllByUser(member);

        List<PostMineResponse> postResponses = posts.stream()
                .map(post -> PostMineResponse.builder()
                        .postId(post.getPostId())
                        .diaryText(post.getDiaryText())
                        .emoji(post.getEmoji())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .isShared(post.isShared())
                        .likeCount(post.getLikeCount())
                        .build())
                .collect(Collectors.toList());

        return ApiResponse.success(postResponses);
    }

    private String resolveTokenFromRequest() {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new CoreException(ExceptionType.INVALID_TOKEN);
    }
}