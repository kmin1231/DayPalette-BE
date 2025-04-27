// posts/service/PostDetailService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.core.response.ApiResponse;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import com.khu.cloud.diary.posts.dto.PostDetailResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.repository.PostRepository;
import com.khu.cloud.diary.posts.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDetailService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public ApiResponse<PostDetailResponse> getPostDetail(Long postId, HttpServletRequest request) {
        
        // extract token from HttpServletRequest
        String token = AuthUtil.resolveTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);

        // 사용자 정보 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        // 'postId'로 post 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CoreException(ExceptionType.POST_NOT_FOUND));

        // 사용자 본인이 작성한 글 또는 공개 설정된 post에만 접근 허용
        if (!post.getUser().getUserId().equals(member.getUserId()) && !post.isShared()) {
            throw new CoreException(ExceptionType.ACCESS_DENIED);
        }

        // API response object 생성
        PostDetailResponse response = PostDetailResponse.builder()
                .postId(post.getPostId())
                .diaryText(post.getDiaryText())
                .emoji(post.getEmoji())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .isShared(post.isShared())
                .likeCount(post.getLikeCount())
                .build();

        return ApiResponse.success(response);
    }
}