/*
package com.khu.cloud.diary.community.entity.DiaryPost;

import com.khu.cloud.diary.community.entity.BaseTimeEntity;
import com.khu.cloud.diary.community.entity.Users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class DiaryPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;


    // @Column(length = 50, nullable = false)
    // private String title;


    @Column(length = 500, nullable = false)
    private String content;

    @Column
    private String imageUrl;
    // post에 이미지 하나면 Url이 낫지 않을까?

    @Column(nullable = false)
    private String emotionTag;

    @Column(nullable = false)
    private boolean isShared;

    @Column(nullable = false) // 초기값을 0으로 두고 싶어.
    private int likeCount;


    @Builder
    public DiaryPost(Users user, String content, //String title
                     String emotionTag, boolean isShared) {
        this.user = user;
        // this.title = title;
        this.content = content;
        this.emotionTag = emotionTag;
        this.isShared = isShared;
        this.likeCount = 0;
    }
}
*/