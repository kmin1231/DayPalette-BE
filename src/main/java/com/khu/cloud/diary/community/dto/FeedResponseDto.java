package com.khu.cloud.diary.community.dto;

import com.khu.cloud.diary.posts.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FeedResponseDto {

    @Schema(description = "게시글 ID")
    private Long postId;

    @Schema(description = "작성자 닉네임", example = "cloudUser")
    private String nickname;

    @Schema(description = "다이어리 요약(앞 30자)", example = "오늘은 카페에서 비를 보며...")
    private String title;       // 다이어리 텍스트 앞 30자

    @Schema(description = "이미지 URL", example = "https://.../image.png")
    private String imageUrl;

    //private String emotion;

    @Schema(description = "좋아요 수", example = "13")
    private int likeCount;

    @Schema(description = "작성 시각(UTC)", example = "2025-05-13T12:30:21")
    private LocalDateTime createdAt;

    public FeedResponseDto(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getDiaryText().substring(0, Math.min(30, post.getDiaryText().length())); // 피드에 간략히
        this.imageUrl = post.getImageUrl();
        //this.emotion = post.getEmoji();  // Enum으로 toString()
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
    }
}
