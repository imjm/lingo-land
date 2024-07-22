package com.ssafy.a603.lingoland.group.entity;

import java.time.LocalDateTime;

import com.ssafy.a603.lingoland.member.entity.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember {

	@EmbeddedId
	private GroupMemberId id;

	private String description;

	private LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime deletedAt;

	private boolean isDeleted;

	@MapsId("groupId")
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	@MapsId("memberId")
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public GroupMember(GroupMemberId id, String description) {
		this.id = id;
		this.description = description;
		this.createdAt = LocalDateTime.now();
	}

	public void quit() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}

	public void addMember(Member member) {
		this.member = member;
	}

	public void removeMember() {
		this.member = null;
	}

	public void addGroup(Group group) {
		this.group = group;
	}

	public void removeGroup() {
		this.group = null;
	}
}