// member/repository/MemberRepository.java

package com.khu.cloud.diary.member.repository;

import com.khu.cloud.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}