// core/response/ApiResponse.java

package com.khu.cloud.diary.core.response;

import com.khu.cloud.diary.core.exception.ErrorMessage;

public record ApiResponse<T>(ResultType result, T data, ErrorMessage error) {

    // sucessful response without data
    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    // successful response with data
    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    // error response
    public static ApiResponse<?> error(ErrorMessage error) {
        return new ApiResponse<>(ResultType.ERROR, null, error);
    }

}