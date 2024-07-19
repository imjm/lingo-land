package com.ssafy.a603.lingoland.group.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.member.entity.Member;

class GroupDiffblueTest {
	/**
	 * Method under test: {@link Group#updateGroup(UpdateGroupDTO)}
	 */
	@Test
	void testUpdateGroup() {
		// Arrange
		Group group = new Group();

		// Act
		group.updateGroup(new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something"));

		// Assert
		assertEquals("Name", group.getName());
		assertEquals("The characteristics of someone or something", group.getDescription());
		assertEquals(1, group.getPassword().intValue());
	}

	/**
	 * Method under test: {@link Group#delete()}
	 */
	@Test
	void testDelete() {
		// Arrange
		Group group = new Group();

		// Act
		group.delete();

		// Assert
		assertTrue(group.isDeleted());
	}

	/**
	 * Method under test:
	 * {@link Group#Group(String, Integer, String, String, Member)}
	 */
	@Test
	void testNewGroup() {
		// Arrange
		Member leader = new Member();

		// Act
		Group actualGroup = new Group("Name", 1, "The characteristics of someone or something", "Group Image", leader);

		// Assert
		assertEquals("Group Image", actualGroup.getGroupImage());
		assertEquals("Name", actualGroup.getName());
		assertEquals("The characteristics of someone or something", actualGroup.getDescription());
		assertNull(actualGroup.getId());
		assertNull(actualGroup.getDeletedAt());
		assertEquals(0, actualGroup.getMemberCount());
		assertEquals(1, actualGroup.getPassword().intValue());
		assertFalse(actualGroup.isDeleted());
		assertTrue(actualGroup.getGroupMembers().isEmpty());
		assertSame(leader, actualGroup.getLeader());
	}

	/**
	 * Method under test: {@link Group#setGroupImagePath(String)}
	 */
	@Test
	void testSetGroupImagePath() {
		// TODO: Diffblue Cover was only able to create a partial test for this method:
		//   Reason: Missing observers.
		//   Diffblue Cover was unable to create an assertion.
		//   Add getters for the following fields or make them package-private:
		//     Group.createdAt
		//     Group.deletedAt
		//     Group.description
		//     Group.groupImage
		//     Group.groupMembers
		//     Group.id
		//     Group.isDeleted
		//     Group.leader
		//     Group.memberCount
		//     Group.name
		//     Group.password

		// Arrange
		Group group = new Group();
		group.join(new GroupMember());
		group.setGroupImagePath("Path");

		GroupMember groupMember = new GroupMember();
		groupMember.addGroup(group);
		groupMember.addMember(new Member());

		Group group2 = new Group();
		group2.join(groupMember);
		group2.setGroupImagePath("Path");

		GroupMember groupMember2 = new GroupMember();
		groupMember2.addGroup(group2);
		groupMember2.addMember(new Member());

		Group group3 = new Group();
		group3.join(groupMember2);
		group3.setGroupImagePath("Path");

		// Act
		group3.setGroupImagePath("Path");
	}

	/**
	 * Method under test: {@link Group#join(GroupMember)}
	 */
	@Test
	void testJoin() {
		// Arrange
		Group group = new Group();

		Group group2 = new Group();
		group2.join(new GroupMember());
		group2.setGroupImagePath("Path");

		GroupMember groupMember = new GroupMember();
		groupMember.addGroup(group2);
		groupMember.addMember(new Member());

		Group group3 = new Group();
		group3.join(groupMember);
		group3.setGroupImagePath("Path");

		GroupMember groupMember2 = new GroupMember();
		groupMember2.addGroup(group3);
		groupMember2.addMember(new Member());

		// Act
		group.join(groupMember2);

		// Assert
		List<GroupMember> groupMembers = group.getGroupMembers();
		assertEquals(1, groupMembers.size());
		assertEquals(2, group.getMemberCount());
		assertSame(groupMember2, groupMembers.get(0));
	}
}
