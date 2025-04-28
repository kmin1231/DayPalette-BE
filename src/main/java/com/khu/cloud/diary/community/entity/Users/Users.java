/*
package com.khu.cloud.diary.community.entity.Users;

import com.khu.cloud.diary.community.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Builder
    public Users(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
*/
