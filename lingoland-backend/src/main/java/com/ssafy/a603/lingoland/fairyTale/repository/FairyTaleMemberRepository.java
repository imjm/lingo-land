package com.ssafy.a603.lingoland.fairyTale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMember;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMemberId;

@Repository
public interface FairyTaleMemberRepository extends JpaRepository<FairyTaleMember, FairyTaleMemberId> {
}
