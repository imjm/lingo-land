package com.ssafy.a603.lingoland.fairyTale.entity;

import java.time.LocalDateTime;

import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.member.entity.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FairyTaleMember {

	@EmbeddedId
	private FairyTaleMemberId id;

	private boolean isVisible;
	private LocalDateTime invisibleAt;

	@MapsId("groupId")
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	@MapsId("memberId")
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
