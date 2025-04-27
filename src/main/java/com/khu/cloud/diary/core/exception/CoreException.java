// core/exception//CoreException.java

package com.khu.cloud.diary.core.exception;


public class CoreException extends RuntimeException {

    private final ExceptionType exceptionType;

    public CoreException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}