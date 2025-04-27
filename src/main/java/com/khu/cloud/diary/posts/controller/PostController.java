// posts/controller/PostController.java

package com.khu.cloud.diary.posts.controller;

import com.khu.cloud.diary.posts.dto.PostMineResponse;
import com.khu.cloud.diary.posts.service.PostReadService;
import com.khu.cloud.diary.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostReadService postReadService;

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<PostMineResponse>>> getMyPosts() {
        
        ApiResponse<List<PostMineResponse>> myPosts = postReadService.getMyPosts();
        return ResponseEntity.ok(myPosts);
    }
}
