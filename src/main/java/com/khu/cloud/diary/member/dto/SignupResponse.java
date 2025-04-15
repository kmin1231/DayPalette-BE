// SignupResponse.java (응답 DTO)
package com.khu.cloud.diary.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private Long id;
    private String email;
    private String nickname;

    public SignupResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}