package com.ssafy.a603.lingoland.group.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ssafy.a603.lingoland.member.entity.Member;

class GroupMemberDiffblueTest {
	/**
	 * Method under test:
	 * {@link GroupMember#GroupMember(GroupMemberId, String, Group, Member)}
	 */
	@Test
	void testNewGroupMember() {
		// Arrange
		GroupMemberId id = new GroupMemberId();

		Group group = new Group();
		group.setGroupImagePath("Path");
		Member member = new Member();

		// Act
		GroupMember actualGroupMember = new GroupMember(id, "The characteristics of someone or something", group,
			member);

		// Assert
		assertEquals("The characteristics of someone or something", actualGroupMember.getDescription());
		assertNull(actualGroupMember.getCreatedAt());
		assertNull(actualGroupMember.getDeletedAt());
		assertFalse(actualGroupMember.isDeleted());
		assertSame(group, actualGroupMember.getGroup());
		assertSame(id, actualGroupMember.getId());
		assertSame(member, actualGroupMember.getMember());
	}
}
