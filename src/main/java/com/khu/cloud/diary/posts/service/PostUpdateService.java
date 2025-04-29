// posts/service/PostUpdateService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.core.response.ApiResponse;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import com.khu.cloud.diary.posts.dto.PostUpdateRequest;
import com.khu.cloud.diary.posts.dto.PostUpdateResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.repository.PostRepository;

import static com.khu.cloud.diary.posts.util.AuthUtil.resolveTokenFromRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponse<PostUpdateResponse> updatePost(Long postId, PostUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = resolveTokenFromRequest(httpServletRequest);
        String email = jwtUtil.extractEmail(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CoreException(ExceptionType.POST_NOT_FOUND));

        if (!post.getUser().getUserId().equals(member.getUserId())) {
            throw new CoreException(ExceptionType.ACCESS_DENIED);
        }

        post.setDiaryText(request.getDiaryText());
        post.setEmoji(request.getEmoji());
        post.setShared(request.isShared());

        postRepository.save(post);

        PostUpdateResponse response = PostUpdateResponse.builder()
                .postId(post.getPostId())
                .message("Post updated successfully.")
                .build();

        return ApiResponse.success(response);
    }
}