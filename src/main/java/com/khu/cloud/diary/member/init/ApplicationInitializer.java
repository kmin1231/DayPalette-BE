// member/init/ApplicationInitializer.java

package com.khu.cloud.diary.member.init;

import com.khu.cloud.diary.member.service.MemberService;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class ApplicationInitializer {

    private final MemberService memberService;

    public ApplicationInitializer(MemberService memberService) {
        this.memberService = memberService;
    }

    // 서버 시작할 때 임시 사용자 생성
    @PostConstruct
    public void init() {
        memberService.createTemporaryUser();
    }
}