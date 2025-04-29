// posts/dto/PostDeleteResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDeleteResponse {
    private String message;
}