// posts/service/PostLikeService.java

package com.khu.cloud.diary.posts.service;

import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.core.response.ApiResponse;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import com.khu.cloud.diary.posts.dto.PostLikeResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.repository.PostRepository;

import static com.khu.cloud.diary.posts.util.AuthUtil.resolveTokenFromRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponse<PostLikeResponse> likePost(Long postId, HttpServletRequest httpServletRequest) {
        String token = resolveTokenFromRequest(httpServletRequest);
        String email = jwtUtil.extractEmail(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CoreException(ExceptionType.POST_NOT_FOUND));

        // 해당 post에 이미 '좋아요'를 눌렀는지 확인
        if (post.getLikedUsers().contains(member)) {
            throw new CoreException(ExceptionType.POST_ALREADY_LIKED);
        }

        // '좋아요' 추가
        post.getLikedUsers().add(member);

        // update like count
        post.setLikeCount(post.getLikedUsers().size());
        
        postRepository.save(post);


        // boolean liked = !post.getLikedUsers().contains(member);
        // if (liked) {
        //     post.getLikedUsers().add(member);
        // } else {
        //     post.getLikedUsers().remove(member);
        // }

        post.setLikeCount(post.getLikedUsers().size());

        postRepository.save(post);

        PostLikeResponse response = PostLikeResponse.builder()
                .postId(post.getPostId())
                .liked(true)
                .likeCount(post.getLikeCount())
                .message("Post liked successfully.")
                .build();

        return ApiResponse.success(response);
    }

    @Transactional
    public ApiResponse<PostLikeResponse> unlikePost(Long postId, HttpServletRequest httpServletRequest) {
        String token = resolveTokenFromRequest(httpServletRequest);
        String email = jwtUtil.extractEmail(token);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CoreException(ExceptionType.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CoreException(ExceptionType.POST_NOT_FOUND));

        // 해당 post에 좋아요를 눌렀는지 확인
        if (!post.getLikedUsers().contains(member)) {
            throw new CoreException(ExceptionType.POST_NOT_LIKED);
        }

        // '좋아요' 취소
        post.getLikedUsers().remove(member);

        // update like count
        post.setLikeCount(post.getLikedUsers().size());

        postRepository.save(post);

        PostLikeResponse response = PostLikeResponse.builder()
                .postId(post.getPostId())
                .liked(false)
                .likeCount(post.getLikeCount())
                .message("Like removed successfully.")
                .build();

        return ApiResponse.success(response);
    }
}