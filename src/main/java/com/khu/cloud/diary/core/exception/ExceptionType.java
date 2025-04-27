// core/exception/ExceptionType.java

package com.khu.cloud.diary.core.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    // client side errors (4xx)
    E400_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    E401_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    E403_FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden access"),
    E404_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),

    // server side errors (5xx)
    E500_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    E502_BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "Bad gateway"),
    E503_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable"),
    
    // custom errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
    
    private final HttpStatus status;
    private final String message;

    ExceptionType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}