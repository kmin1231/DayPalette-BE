package com.khu.cloud.diary.member.controller;

import com.khu.cloud.diary.member.dto.MemberInfoResponse;
import com.khu.cloud.diary.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API", description = "사용자 정보 관련 API") // Swagger 문서화를 위한 태그
@RestController
@RequestMapping("/api/users") // API 요청 경로의 기본 prefix
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info", description = "JWT 토큰을 통해 현재 로그인된 사용자의 정보를 반환합니다.")
    // @PreAuthorize("isAuthenticated()") // SecurityConfig에서 경로별 접근 제어를 하고 있으므로, 여기서는 생략 가능
    public ResponseEntity<MemberInfoResponse> getCurrentUserInfo() {
        MemberInfoResponse memberInfo = memberService.getCurrentMemberInfo();
        return ResponseEntity.ok(memberInfo);
    }
}