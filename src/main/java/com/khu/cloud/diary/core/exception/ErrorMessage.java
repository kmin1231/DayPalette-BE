// core/exception//ErrorMessage.java

package com.khu.cloud.diary.core.exception;

public class ErrorMessage {
    private String message;
    private String code;

    public ErrorMessage(ExceptionType exceptionType) {
        this.message = exceptionType.getMessage();
        this.code = exceptionType.name();
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}