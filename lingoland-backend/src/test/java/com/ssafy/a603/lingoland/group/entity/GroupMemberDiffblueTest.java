package com.ssafy.a603.lingoland.group.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ssafy.a603.lingoland.member.entity.Member;

class GroupMemberDiffblueTest {
	/**
	 * Methods under test:
	 * <ul>
	 *   <li>{@link GroupMember#addGroup(Group)}
	 *   <li>{@link GroupMember#addMember(Member)}
	 *   <li>{@link GroupMember#removeGroup()}
	 *   <li>{@link GroupMember#removeMember()}
	 * </ul>
	 */
	@Test
	void testGettersAndSetters() {
		// TODO: Diffblue Cover was only able to create a partial test for this method:
		//   Reason: Missing observers.
		//   Diffblue Cover was unable to create an assertion.
		//   Add getters for the following fields or make them package-private:
		//     GroupMember.createdAt
		//     GroupMember.deletedAt
		//     GroupMember.description
		//     GroupMember.group
		//     GroupMember.id
		//     GroupMember.isDeleted
		//     GroupMember.member

		// Arrange
		GroupMember groupMember = new GroupMember();
		groupMember.addGroup(new Group());
		groupMember.addMember(new Member());

		Group group = new Group();
		group.join(groupMember);
		group.setGroupImagePath("Path");

		GroupMember groupMember2 = new GroupMember();
		groupMember2.addGroup(group);
		groupMember2.addMember(new Member());

		Group group2 = new Group();
		group2.join(groupMember2);
		group2.setGroupImagePath("Path");

		GroupMember groupMember3 = new GroupMember();
		groupMember3.addGroup(group2);
		groupMember3.addMember(new Member());

		GroupMember groupMember4 = new GroupMember();
		groupMember4.addGroup(new Group());
		groupMember4.addMember(new Member());

		Group group3 = new Group();
		group3.join(groupMember4);
		group3.setGroupImagePath("Path");

		GroupMember groupMember5 = new GroupMember();
		groupMember5.addGroup(group3);
		groupMember5.addMember(new Member());

		Group group4 = new Group();
		group4.join(groupMember5);
		group4.setGroupImagePath("Path");

		// Act
		groupMember3.addGroup(group4);
		groupMember3.addMember(new Member());
		groupMember3.removeGroup();
		groupMember3.removeMember();
	}

	/**
	 * Methods under test:
	 * <ul>
	 *   <li>{@link GroupMember#addGroup(Group)}
	 *   <li>{@link GroupMember#addMember(Member)}
	 *   <li>{@link GroupMember#removeGroup()}
	 *   <li>{@link GroupMember#removeMember()}
	 * </ul>
	 */
	@Test
	void testGettersAndSetters2() {
		// TODO: Diffblue Cover was only able to create a partial test for this method:
		//   Reason: Missing observers.
		//   Diffblue Cover was unable to create an assertion.
		//   Add getters for the following fields or make them package-private:
		//     GroupMember.createdAt
		//     GroupMember.deletedAt
		//     GroupMember.description
		//     GroupMember.group
		//     GroupMember.id
		//     GroupMember.isDeleted
		//     GroupMember.member

		// Arrange
		GroupMember groupMember = new GroupMember();
		groupMember.addGroup(new Group());
		groupMember.addMember(new Member());

		Group group = new Group();
		group.join(groupMember);
		group.setGroupImagePath("Path");

		Member member = new Member();
		member.updateRefreshToken("Refresh");

		GroupMember groupMember2 = new GroupMember();
		groupMember2.addGroup(group);
		groupMember2.addMember(member);

		Group group2 = new Group();
		group2.join(groupMember2);
		group2.setGroupImagePath("Path");

		Member member2 = new Member();
		member2.updateRefreshToken("Refresh");

		GroupMember groupMember3 = new GroupMember();
		groupMember3.addGroup(group2);
		groupMember3.addMember(member2);

		GroupMember groupMember4 = new GroupMember();
		groupMember4.addGroup(new Group());
		groupMember4.addMember(new Member());

		Group group3 = new Group();
		group3.join(groupMember4);
		group3.setGroupImagePath("Path");

		Member member3 = new Member();
		member3.updateRefreshToken("Refresh");

		GroupMember groupMember5 = new GroupMember();
		groupMember5.addGroup(group3);
		groupMember5.addMember(member3);

		Group group4 = new Group();
		group4.join(groupMember5);
		group4.setGroupImagePath("Path");

		// Act
		groupMember3.addGroup(group4);
		Member member4 = new Member();
		member4.updateRefreshToken("Refresh");
		groupMember3.addMember(member4);
		groupMember3.removeGroup();
		groupMember3.removeMember();
	}

	/**
	 * Method under test: {@link GroupMember#quit()}
	 */
	@Test
	void testQuit() {
		// Arrange
		GroupMember groupMember = new GroupMember();

		// Act
		groupMember.quit();

		// Assert
		assertTrue(groupMember.isDeleted());
	}

	/**
	 * Method under test: {@link GroupMember#GroupMember(GroupMemberId, String)}
	 */
	@Test
	void testNewGroupMember() {
		// Arrange
		GroupMemberId id = new GroupMemberId();

		// Act
		GroupMember actualGroupMember = new GroupMember(id, "The characteristics of someone or something");

		// Assert
		assertEquals("The characteristics of someone or something", actualGroupMember.getDescription());
		assertNull(actualGroupMember.getGroup());
		assertNull(actualGroupMember.getMember());
		assertNull(actualGroupMember.getDeletedAt());
		assertFalse(actualGroupMember.isDeleted());
		assertSame(id, actualGroupMember.getId());
	}
}
