// posts/entity/Post.java

package com.khu.cloud.diary.posts.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.khu.cloud.diary.member.entity.Member;

@Entity
@Table(name = "posts")  // table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @Column(nullable = false)
    private String diaryText;

    @Column(nullable = false)
    private String emoji;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean isShared = false;  // default

    @Column(nullable = false)
    @Builder.Default
    private int likeCount = 0;  // default


    // post에 '좋아요' 피드백을 남긴 사용자 목록을 저장하기 위한 'ManyToMany' 관계 설정
    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<Member> likedUsers = new HashSet<>();

    public Set<Member> getLikedUsers() {
        return likedUsers;
    }
}