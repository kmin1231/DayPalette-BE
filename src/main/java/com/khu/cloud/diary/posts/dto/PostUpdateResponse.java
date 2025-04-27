// posts/dto/PostUpdateResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostUpdateResponse {
    private Long postId;
    private String message;
}