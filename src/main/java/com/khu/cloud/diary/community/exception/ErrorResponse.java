package com.khu.cloud.diary.community.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;  // 에러 발생 시간
    private int status;               // HTTP Status Code
    private String error;             // 에러 이름
    private String message;           // 상세 메시지
}
