package com.khu.cloud.diary.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}