package com.khu.cloud.diary.community.dto;

import com.khu.cloud.diary.posts.entity.Post;
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

    public DiaryPostFeedResponseDto(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getDiaryText().substring(0, Math.min(30, post.getDiaryText().length())); // 피드에 간략히
        this.imageUrl = post.getImageUrl();
        this.emotion = post.getEmoji();  // Enum으로 toString()
        this.sympathyCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
    }
}
