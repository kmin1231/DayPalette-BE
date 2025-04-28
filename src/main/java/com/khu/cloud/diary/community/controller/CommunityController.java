package com.khu.cloud.diary.community.controller;


import com.khu.cloud.diary.community.dto.DiaryPostFeedResponseDto;
import com.khu.cloud.diary.community.service.CommunityService;
import com.khu.cloud.diary.community.type.FeedSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // ✅ REST API용 Controller 선언
@RequiredArgsConstructor  // ✅ final 필드를 생성자로 주입
@RequestMapping("/api/community") // ✅ API 베이스 URL 지정
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/feed")
    public List<DiaryPostFeedResponseDto> getFeed(
            @RequestParam(defaultValue = "CREATED_AT") FeedSortType sort,
            @RequestParam(required = false) String emotion) {

        return communityService.getFeed(sort, emotion);
    }
}