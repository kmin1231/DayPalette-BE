// member/dto/AuthRequest.java

package com.khu.cloud.diary.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @Schema(description = "user email", example = "daypalette2025@gmail.com")
    private String email;

    @Schema(description = "user password", example = "cloudcomputing")
    private String password;
}