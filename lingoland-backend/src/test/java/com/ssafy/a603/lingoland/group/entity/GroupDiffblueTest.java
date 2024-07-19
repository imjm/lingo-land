package com.ssafy.a603.lingoland.group.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.member.entity.Member;

class GroupDiffblueTest {
	/**
	 * Method under test: {@link Group#updateUser(UpdateGroupDTO)}
	 */
	@Test
	void testUpdateUser() {
		// Arrange
		Group group = new Group();

		// Act
		group.updateUser(new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something"));

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
	 * Methods under test:
	 * <ul>
	 *   <li>{@link Group#Group(String, Integer, String, String, Member)}
	 *   <li>{@link Group#setGroupImagePath(String)}
	 * </ul>
	 */
	@Test
	void testGettersAndSetters() {
		// Arrange
		Member leader = new Member();

		// Act
		Group actualGroup = new Group("Name", 1, "The characteristics of someone or something", "Group Image", leader);
		actualGroup.setGroupImagePath("Path");

		// Assert
		assertEquals("Name", actualGroup.getName());
		assertEquals("Path", actualGroup.getGroupImage());
		assertEquals("The characteristics of someone or something", actualGroup.getDescription());
		assertNull(actualGroup.getId());
		assertNull(actualGroup.getCreatedAt());
		assertNull(actualGroup.getDeletedAt());
		assertEquals(1, actualGroup.getMemberCount());
		assertEquals(1, actualGroup.getPassword().intValue());
		assertFalse(actualGroup.isDeleted());
		assertTrue(actualGroup.getGroupMembers().isEmpty());
		assertSame(leader, actualGroup.getLeader());
	}
}
