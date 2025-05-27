// posts/util/AuthUtil.java

package com.khu.cloud.diary.posts.util;

import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {
    /*
         - 인증 필수가 아닌 엔드포인트에서 사용.
         - Authorization 헤더 없으면 null 반환
         - "Bearer " 형식이 맞아야 토큰 리턴
     */
    public static String resolveTokenOrNull(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);             // "Bearer " 이후 문자열
        }
        return null;                                // 헤더 없거나 형식 불일치
    }

    public static String resolveTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new CoreException(ExceptionType.INVALID_TOKEN);
    }
}