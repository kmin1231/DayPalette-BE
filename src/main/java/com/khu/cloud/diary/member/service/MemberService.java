// member/service/MemberService.java

package com.khu.cloud.diary.member.service;

import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import com.khu.cloud.diary.core.exception.InvalidRequestArgumentException;
import com.khu.cloud.diary.member.dto.MemberInfoResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
                .build();

            memberRepository.save(temporaryUser);
            System.out.println("Temporary user created: daypalette@cloud.com, cloudcomputing");
        } else {
            // System.out.println("Temporary user already exists");
        }
    }

    @Transactional(readOnly = true) // member 자신의 개인 정보 조회
    public MemberInfoResponse getCurrentMemberInfo() {
        // 1. SecurityContextHolder에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            // 인증 정보가 없거나, 인증되지 않았거나, 익명 사용자인 경우
            // 이 로직은 JWT 필터가 제대로 동작하여 인증된 사용자만 이 메소드에 접근한다고 가정할 때,
            // 사실상 발생하기 어렵습니다. SecurityConfig에서 경로 접근 제어를 하기 때문입니다.
            // 하지만 방어적으로 코드를 작성합니다.
            throw new InvalidRequestArgumentException("User not authenticated."); // 또는 적절한 인증 예외
        }

        String userEmail;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            userEmail = (String) principal;
        } else {
            // 예상치 못한 Principal 타입
            throw new InvalidRequestArgumentException("Invalid authentication principal type.");
        }

        // 2. 이메일을 사용하여 MemberRepository에서 사용자 정보 조회
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new InvalidRequestArgumentException("User not found with email: " + userEmail)); // 사용자를 찾을 수 없는 경우 예외

        // 3. Member 엔티티를 MemberInfoResponse DTO로 변환하여 반환
        // MemberInfoResponse에 fromEntity와 같은 정적 팩토리 메소드가 있다면 사용하는 것이 좋습니다.
        // 여기서는 직접 생성자를 호출합니다.
        return new MemberInfoResponse(
                member.getUserId(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}