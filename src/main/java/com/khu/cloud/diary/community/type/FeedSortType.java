package com.khu.cloud.diary.community.type;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Community 피드 정렬 타입")
public enum FeedSortType {
    @Schema(description = "최신순")
    CREATED_AT("createdAt"),   // 최신순

    @Schema(description = "좋아요순")
    LIKE("like");              // 인기순

    private final String value;     // URL 파라미터로 받을 문자열

    FeedSortType(String value) {     // enum 생성자
        this.value = value;
    }

    public String getValue() {       // 외부에서 읽기용 getter
        return value;
    }    // 외부에서 읽기용 getter
}