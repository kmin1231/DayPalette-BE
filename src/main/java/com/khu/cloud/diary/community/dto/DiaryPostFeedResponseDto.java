package com.khu.cloud.diary.community.dto;

import com.khu.cloud.diary.community.entity.DiaryPost.DiaryPost;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DiaryPostFeedResponseDto {
    private Long postId;
    private String nickname;
    private String title;
    private String imageUrl;
    private String emotion;
    private int sympathyCount;
    private LocalDateTime createdAt;

    public DiaryPostFeedResponseDto(DiaryPost post) {
        this.postId = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getContent().substring(0, Math.min(30, post.getContent().length())); // 피드에 간략히
        this.imageUrl = post.getImageUrl();
        this.emotion = post.getEmotionTag();  // Enum이면 toString()
        this.sympathyCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
    }
}
