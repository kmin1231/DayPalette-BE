package com.khu.cloud.diary.community.type;

public enum FeedSortType {
    CREATED_AT("createdAt"),
    LIKE("like");

    private final String value;

    FeedSortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}