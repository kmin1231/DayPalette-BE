// posts/controller/PostController.java

package com.khu.cloud.diary.posts.controller;

import com.khu.cloud.diary.posts.dto.*;
import com.khu.cloud.diary.posts.service.*;
import com.khu.cloud.diary.core.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostMineService postmMineService;
    private final PostDetailService postDetailService;
    private final PostUpdateService postUpdateService;
    private final PostShareUpdateService postShareUpdateService;
    private final PostDeleteService postDeleteService;
    private final PostLikeService postLikeService;

    // 사용자의 post 조회
    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<PostMineResponse>>> getMyPosts() {
        ApiResponse<List<PostMineResponse>> myPosts = postmMineService.getMyPosts();
        return ResponseEntity.ok(myPosts);
    }

    // 특정 post 조회
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPostDetail(@PathVariable Long postId, HttpServletRequest request) {
        return postDetailService.getPostDetail(postId, request);
    }

    // [PUT] 특정 post 수정
    @PutMapping("/{postId}/update")
    public ApiResponse<PostUpdateResponse> updatePost(@PathVariable Long postId, 
                                                      @RequestBody PostUpdateRequest request,
                                                      HttpServletRequest httpServletRequest) {
        return postUpdateService.updatePost(postId, request, httpServletRequest);
    }

    // [PATCH] 특정 post의 공개 여부만 수정
    @PatchMapping("/{postId}/share")
    public ApiResponse<PostShareUpdateResponse> updatePostShare(@PathVariable Long postId,
                                                                @RequestBody PostShareUpdateRequest request,
                                                                HttpServletRequest httpServletRequest) {
        return postShareUpdateService.updatePostShare(postId, request, httpServletRequest);
    }

    // 특정 post 삭제
    @DeleteMapping("/{postId}")
    public ApiResponse<PostDeleteResponse> deletePost(@PathVariable Long postId, HttpServletRequest httpServletRequest) {
        return postDeleteService.deletePost(postId, httpServletRequest);
    }

    // 특정 post에 대한 '좋아요' 여부 확인
    @GetMapping("/{postId}/like/status")
    public ApiResponse<Boolean> getPostLikeStatus(@PathVariable Long postId, HttpServletRequest request) {
        return postLikeService.checkIfPostLiked(postId, request);
    }

    // 특정 post에 대해 '좋아요' 피드백 추가
    @PostMapping("/{postId}/like")
    public ApiResponse<PostLikeResponse> likePost(@PathVariable Long postId, HttpServletRequest httpServletRequest) {
        return postLikeService.likePost(postId, httpServletRequest);
    }

    // 특정 post에 대한 '좋아요' 피드백 취소
    @DeleteMapping("/{postId}/like")
    public ApiResponse<PostLikeResponse> unlikePost(@PathVariable Long postId, HttpServletRequest httpServletRequest) {
        return postLikeService.unlikePost(postId, httpServletRequest);
    }
}