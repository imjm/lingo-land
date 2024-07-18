package com.ssafy.a603.lingoland.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByLoginId(String loginId);

    Member findByLoginId(String loginId);
}
