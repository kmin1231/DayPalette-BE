// posts/dto/GenerateImageResponse.java

package com.khu.cloud.diary.posts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateImageResponse {
    private byte[] diaryImage;
}