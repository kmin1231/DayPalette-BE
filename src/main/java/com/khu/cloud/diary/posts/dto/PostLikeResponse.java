// posts/dto/PostLikeResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikeResponse {
    private Long postId;
    private boolean liked;
    private int likeCount;
    private String message;
}
