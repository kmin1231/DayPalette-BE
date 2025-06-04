package com.khu.cloud.diary.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private Long userId;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
}
