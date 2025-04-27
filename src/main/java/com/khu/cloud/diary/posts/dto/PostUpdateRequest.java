// posts/dto/PostUpdateRequest.java

package com.khu.cloud.diary.posts.dto;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private String diaryText;
    private String emoji;
    private boolean isShared;
}