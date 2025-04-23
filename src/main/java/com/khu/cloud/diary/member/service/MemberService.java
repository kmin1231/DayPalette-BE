// member/service/MemberService.java

package com.khu.cloud.diary.member.service;

import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 임시 사용자 생성
    public void createTemporaryUser() {
        if (!memberRepository.findByEmail("daypalette@cloud.com").isPresent()) {

            String encodedPassword = passwordEncoder.encode("cloudcomputing");

            Member temporaryUser = Member.builder()
                .email("daypalette@cloud.com")    // email
                .password(encodedPassword)              // password
                .nickname("daypalette")        // nickname
                .createdAt(LocalDateTime.now())
                .build();

            memberRepository.save(temporaryUser);
            System.out.println("Temporary user created: daypalette@cloud.com, cloudcomputing");
        } else {
            // System.out.println("Temporary user already exists");
        }
    }
}