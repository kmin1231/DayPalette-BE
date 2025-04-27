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

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<PostMineResponse>>> getMyPosts() {
        ApiResponse<List<PostMineResponse>> myPosts = postmMineService.getMyPosts();
        return ResponseEntity.ok(myPosts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPostDetail(@PathVariable Long postId, HttpServletRequest request) {
        return postDetailService.getPostDetail(postId, request);
    }
}