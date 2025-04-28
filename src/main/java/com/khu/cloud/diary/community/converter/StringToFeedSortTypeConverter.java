package com.khu.cloud.diary.community.converter;

import com.khu.cloud.diary.community.type.FeedSortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFeedSortTypeConverter implements Converter<String, FeedSortType> {

    @Override
    public FeedSortType convert(String source) {
        if (source == null || source.isBlank()) {
            return FeedSortType.CREATED_AT; // 기본값 처리 (optional)
        }

        for (FeedSortType type : FeedSortType.values()) {
            if (type.getValue().equalsIgnoreCase(source)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid FeedSortType: " + source);
    }
}
