package com.khu.cloud.diary.core.exception; // 1. 패키지 선언

import org.springframework.http.HttpStatus; // 2. HttpStatus 임포트
import org.springframework.web.bind.annotation.ResponseStatus; // 3. @ResponseStatus 어노테이션 임포트

@ResponseStatus(HttpStatus.BAD_REQUEST) // 4. @ResponseStatus 어노테이션
public class InvalidRequestArgumentException extends RuntimeException { // 5. RuntimeException 상속

    // 6. 생성자 1: 메시지만 받는 경우
    public InvalidRequestArgumentException(String message) {
        super(message); // 부모 클래스(RuntimeException)의 생성자 호출
    }

    // 7. 생성자 2: 메시지와 원인 예외를 함께 받는 경우 (예외 체이닝)
    public InvalidRequestArgumentException(String message, Throwable cause) {
        super(message, cause); // 부모 클래스(RuntimeException)의 생성자 호출
    }
}