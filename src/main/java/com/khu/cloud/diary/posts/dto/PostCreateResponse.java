// posts/dto/PostCreateResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostCreateResponse {
    private Long postId;
    private String diaryText;
    private String emoji;
    private String imageUrl;
    private LocalDateTime createdAt;
}