// posts/dto/PostMineResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostMineResponse {
    private Long postId;
    private String diaryText;
    // private String emoji;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean isShared;
    private int likeCount;
}