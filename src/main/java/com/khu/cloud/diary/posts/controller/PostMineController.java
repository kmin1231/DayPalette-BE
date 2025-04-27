// posts/controller/PostMineController.java

package com.khu.cloud.diary.posts.controller;

import com.khu.cloud.diary.posts.dto.PostMineResponse;
import com.khu.cloud.diary.posts.service.PostMineService;
import com.khu.cloud.diary.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostMineController {

    private final PostMineService postmMineService;

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<PostMineResponse>>> getMyPosts() {
        
        ApiResponse<List<PostMineResponse>> myPosts = postmMineService.getMyPosts();
        return ResponseEntity.ok(myPosts);
    }
}