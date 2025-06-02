package com.khu.cloud.diary.community.converter;

import com.khu.cloud.diary.community.type.FeedSortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFeedSortTypeConverter implements Converter<String, FeedSortType> {

    @Override
    public FeedSortType convert(String source) {
        // ① null 또는 빈 문자열이면 기본값
        if (source == null || source.isBlank()) {
            return FeedSortType.CREATED_AT;
        }

        // ② 대소문자 무시하며 매칭
        for (FeedSortType type : FeedSortType.values()) {
            if (type.getValue().equalsIgnoreCase(source)) {
                return type;
            }
        }

        // ③ 매칭 실패 → 400 Bad Request 로 이어질 예외
        throw new IllegalArgumentException("Invalid FeedSortType: " + source);
    }
}
