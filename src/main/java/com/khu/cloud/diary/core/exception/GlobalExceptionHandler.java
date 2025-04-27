// core/exception/GlobalExceptionHandler.java

package com.khu.cloud.diary.core.exception;

import com.khu.cloud.diary.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle all exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity.status(500).body(ApiResponse.error(new ErrorMessage(ExceptionType.E500_INTERNAL_SERVER_ERROR)));
    }

    // handle specific exceptions
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreException(CoreException e) {
        return ResponseEntity.status(e.getExceptionType().getStatus())
                .body(ApiResponse.error(new ErrorMessage(e.getExceptionType())));
    }
}
