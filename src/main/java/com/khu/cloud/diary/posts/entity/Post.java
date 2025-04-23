// posts/entity/Post.java

package com.khu.cloud.diary.posts.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false)
    private String diaryText;

    @Column(nullable = false)
    private String emoji;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}