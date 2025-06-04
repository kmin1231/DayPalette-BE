package com.khu.cloud.diary.member.service;

import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // Spring Security의 User 클래스
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // 이 클래스가 Spring의 서비스 컴포넌트임을 알립니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어줍니다 (Lombok).
public class MemberUserDetailsService implements UserDetailsService { // UserDetailsService 인터페이스 구현

    private final MemberRepository memberRepository; // DB에서 Member 정보를 가져오기 위함

    /**
     * Spring Security가 사용자 인증 시 호출하는 메소드입니다.
     * @param username 실제로는 우리 시스템의 사용자 이메일이 전달됩니다.
     * @return UserDetails 객체 (사용자 정보와 권한 포함)
     * @throws UsernameNotFoundException 해당 username(이메일)의 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 전달받은 username (실제로는 이메일)을 사용하여 MemberRepository에서 사용자 정보를 조회합니다.
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // 조회된 Member 정보를 바탕으로 Spring Security가 이해할 수 있는 UserDetails 객체를 생성하여 반환합니다.
        // 여기서는 Spring Security가 제공하는 User 클래스를 사용합니다.
        // 1번째 인자: username (여기서는 Member의 이메일)
        // 2번째 인자: password (Member의 암호화된 비밀번호, JWT 인증에서는 직접 사용되지 않음)
        // 3번째 인자: authorities (사용자의 권한 목록, 여기서는 모든 사용자에게 "ROLE_USER" 부여)
        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}