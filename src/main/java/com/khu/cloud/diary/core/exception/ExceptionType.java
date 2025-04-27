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
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 파일 형식입니다."),
    POST_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."),
    POST_NOT_LIKED(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."),
    INVALID_FILE_SIZE(HttpStatus.BAD_REQUEST, "업로드할 수 있는 파일 크기를 초과했습니다."),
    POST_IS_SHARED(HttpStatus.BAD_REQUEST, "이미 공개된 게시글의 상태를 변경할 수 없습니다."),
    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 페이지 요청입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다. 다시 로그인해주세요."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    
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