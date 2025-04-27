// posts/dto/PostShareUpdateResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostShareUpdateResponse {
    private Long postId;
    private boolean isShared;
    private String message;
}