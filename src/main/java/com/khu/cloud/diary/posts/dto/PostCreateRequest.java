// posts/dto/PostCreateRequest.java

package com.khu.cloud.diary.posts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequest {
    private String diaryText;
    private String date;
    private String imageUrl;
}