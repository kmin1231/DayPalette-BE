// posts/dto/PostCreateRequest.java

package com.khu.cloud.diary.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private String diaryText;
    // private String emoji;
}