// posts/dto/PostDetailResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PostDetailResponse {
    private Long postId;
    private String diaryText;
    private String emoji;
    private String imageUrl;
    private LocalDateTime createdAt;
    private boolean isShared;
    private int likeCount;
}
