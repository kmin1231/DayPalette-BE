package com.khu.cloud.diary.member.service;

import com.khu.cloud.diary.member.dto.AuthRequest;
import com.khu.cloud.diary.member.dto.SignupRequest;
import com.khu.cloud.diary.member.dto.AuthResponse;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(MemberRepository memberRepository, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Member signup(SignupRequest signupRequest) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // 새로운 사용자 계정 생성
        Member newMember = new Member();
        newMember.setEmail(signupRequest.getEmail());
        newMember.setPassword(encodedPassword);
        newMember.setNickname(signupRequest.getNickname());
        newMember.setCreatedAt(LocalDateTime.now());

        // 사용자 정보 저장
        Member createdMember = memberRepository.save(newMember);

        return createdMember;
    }

    public AuthResponse login(AuthRequest authRequest) {

        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(authRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // JWT token 생성
        String token = jwtUtil.generateToken(member.getEmail());

        return new AuthResponse(token);
    }
}