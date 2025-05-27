// member/entity/Member.java

package com.khu.cloud.diary.member.entity;

import com.khu.cloud.diary.core.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.khu.cloud.diary.posts.entity.Post;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    // 필요하면 modifiedAt은 BaseTimeEntity쪽에 이미 있음

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts;
    // private List<Post> posts = new ArrayList<>();   // NPE 방지용 초기화

    @Builder
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}