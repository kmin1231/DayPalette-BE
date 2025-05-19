package com.khu.cloud.diary.community.controller;


import com.khu.cloud.diary.community.dto.FeedResponseDto;
import com.khu.cloud.diary.community.service.CommunityService;
import com.khu.cloud.diary.community.type.FeedSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
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

    /*
    @GetMapping("/feed")
    public List<FeedResponseDto> getFeed(
            @RequestParam(defaultValue = "CREATED_AT") FeedSortType sort,
            @RequestParam(required = false) String emotion) {

        return communityService.getFeed(sort, emotion);
    }
    */
    @Operation(
            summary     = "Community Feed 조회",
            description = """
            *공개된(Post.isShared = true)* 게시글을 정렬 기준에 따라 가져옵니다.  
            - 기본 정렬: **CREATED_AT** (최신순)  
            - `sort=LIKE`   : 좋아요 많은 순  
            """
    )
    @Parameters({
            @Parameter(
                    name = "sort",
                    description = "정렬 기준 (기본값: CREATED_AT)",
                    example = "LIKE",
                    schema = @Schema(implementation = FeedSortType.class)
            )
            /*
            @Parameter(
                    name = "page", description = "0부터 시작하는 페이지 번호 (선택)",
                    example = "0"),
            @Parameter(
                    name = "size", description = "한 페이지 당 게시글 수 (선택, 기본 10)",
                    example = "10")
             */
    })
    @GetMapping("/feed")
    public List<FeedResponseDto> getFeed(
            @RequestParam(defaultValue = "CREATED_AT") FeedSortType sort) {

        // AuthUtil 필요 없음 (익명 접근 허용)
        return communityService.getFeed(sort);
    }
}