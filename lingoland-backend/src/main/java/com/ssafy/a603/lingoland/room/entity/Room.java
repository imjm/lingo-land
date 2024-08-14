package com.ssafy.a603.lingoland.room.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.global.entity.BaseTimeEntity;
import com.ssafy.a603.lingoland.member.entity.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("is_deleted is false")
public class Room extends BaseTimeEntity {
	@EmbeddedId
	private RoomId id;

	@OneToMany
	private Set<Member> contributors;

	@OneToOne
	private FairyTale fairyTale;

	private Boolean isDeleted;

	private LocalDateTime deletedAt;

	@Builder
	public Room(String sessionId, Member starter) {
		this.id = new RoomId(sessionId, starter.getId());
		this.contributors = new HashSet<>();
		this.isDeleted = false;
	}

	public void setFairyTale(FairyTale fairyTale) {
		this.fairyTale = fairyTale;
	}

	public void addContributer(Member member) {
		contributors.add(member);
	}

	public void removeContributer(Member member) {
		contributors.remove(member);
	}

	public void delete() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
		this.contributors.clear();
	}
}
