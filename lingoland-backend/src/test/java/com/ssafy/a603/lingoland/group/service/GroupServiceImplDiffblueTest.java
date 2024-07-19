package com.ssafy.a603.lingoland.group.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.entity.GroupMember;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.member.Member;
import com.ssafy.a603.lingoland.util.ImgUtils;

@ContextConfiguration(classes = {GroupServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GroupServiceImplDiffblueTest {
	@MockBean
	private GroupRepository groupRepository;

	@Autowired
	private GroupServiceImpl groupServiceImpl;

	@MockBean
	private ImgUtils imgUtils;

	/**
	 * Method under test:
	 * {@link GroupServiceImpl#create(CreateGroupDTO, MultipartFile)}
	 */
	@Test
	void testCreate() throws IOException {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Member leader = new Member();
		Group buildResult = groupImageResult.leader(leader).name("Name").password(1).build();
		when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);
		when(imgUtils.saveImage(Mockito.<MultipartFile>any(), Mockito.<String>any())).thenReturn("Save Image");
		CreateGroupDTO request = new CreateGroupDTO("Name", 1, "The characteristics of someone or something", 1);

		// Act
		Group actualCreateResult = groupServiceImpl.create(request,
			new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

		// Assert
		verify(imgUtils).saveImage(isA(MultipartFile.class), eq("GROUP"));
		verify(groupRepository).save(isA(Group.class));
		assertEquals("Group Image", actualCreateResult.getGroupImage());
		assertEquals("Name", actualCreateResult.getName());
		assertEquals("The characteristics of someone or something", actualCreateResult.getDescription());
		assertNull(actualCreateResult.getId());
		assertNull(actualCreateResult.getCreatedAt());
		assertNull(actualCreateResult.getDeletedAt());
		assertEquals(1, actualCreateResult.getMemberCount());
		assertEquals(1, actualCreateResult.getPassword().intValue());
		assertFalse(actualCreateResult.isDeleted());
		List<GroupMember> groupMembers = actualCreateResult.getGroupMembers();
		assertTrue(groupMembers.isEmpty());
		assertEquals(groupMembers, groupServiceImpl.findAll());
		assertSame(leader, actualCreateResult.getLeader());
	}

	/**
	 * Method under test:
	 * {@link GroupServiceImpl#create(CreateGroupDTO, MultipartFile)}
	 */
	@Test
	void testCreate2() throws IOException {
		// Arrange
		when(imgUtils.saveImage(Mockito.<MultipartFile>any(), Mockito.<String>any()))
			.thenThrow(new NoSuchElementException("GROUP"));
		CreateGroupDTO request = new CreateGroupDTO("Name", 1, "The characteristics of someone or something", 1);

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.create(request,
			new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
		verify(imgUtils).saveImage(isA(MultipartFile.class), eq("GROUP"));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#findAll()}
	 */
	@Test
	void testFindAll() {
		// Arrange
		ArrayList<Group> groupList = new ArrayList<>();
		when(groupRepository.findAll()).thenReturn(groupList);

		// Act
		List<Group> actualFindAllResult = groupServiceImpl.findAll();

		// Assert
		verify(groupRepository).findAll();
		assertTrue(actualFindAllResult.isEmpty());
		assertSame(groupList, actualFindAllResult);
	}

	/**
	 * Method under test: {@link GroupServiceImpl#findAll()}
	 */
	@Test
	void testFindAll2() {
		// Arrange
		when(groupRepository.findAll()).thenThrow(new NoSuchElementException("foo"));

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.findAll());
		verify(groupRepository).findAll();
	}

	/**
	 * Method under test: {@link GroupServiceImpl#findById(int)}
	 */
	@Test
	void testFindById() {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Member leader = new Member();
		Group buildResult = groupImageResult.leader(leader).name("Name").password(1).build();
		Optional<Group> ofResult = Optional.of(buildResult);
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

		// Act
		Group actualFindByIdResult = groupServiceImpl.findById(1);

		// Assert
		verify(groupRepository).findById(eq(1));
		assertEquals("Group Image", actualFindByIdResult.getGroupImage());
		assertEquals("Name", actualFindByIdResult.getName());
		assertEquals("The characteristics of someone or something", actualFindByIdResult.getDescription());
		assertNull(actualFindByIdResult.getId());
		assertNull(actualFindByIdResult.getCreatedAt());
		assertNull(actualFindByIdResult.getDeletedAt());
		assertEquals(1, actualFindByIdResult.getMemberCount());
		assertEquals(1, actualFindByIdResult.getPassword().intValue());
		assertFalse(actualFindByIdResult.isDeleted());
		List<GroupMember> groupMembers = actualFindByIdResult.getGroupMembers();
		assertTrue(groupMembers.isEmpty());
		assertEquals(groupMembers, groupServiceImpl.findAll());
		assertSame(leader, actualFindByIdResult.getLeader());
	}

	/**
	 * Method under test: {@link GroupServiceImpl#findById(int)}
	 */
	@Test
	void testFindById2() {
		// Arrange
		Optional<Group> emptyResult = Optional.empty();
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.findById(1));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#findById(int)}
	 */
	@Test
	void testFindById3() {
		// Arrange
		when(groupRepository.findById(Mockito.<Integer>any())).thenThrow(new NoSuchElementException("No such group"));

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.findById(1));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test:
	 * {@link GroupServiceImpl#update(UpdateGroupDTO, MultipartFile)}
	 */
	@Test
	void testUpdate() throws IOException {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
		Optional<Group> ofResult = Optional.of(buildResult);
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
		when(imgUtils.saveImage(Mockito.<MultipartFile>any(), Mockito.<String>any())).thenReturn("Save Image");
		doNothing().when(imgUtils).deleteImage(Mockito.<String>any(), Mockito.<String>any());
		UpdateGroupDTO request = new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something");

		// Act
		groupServiceImpl.update(request,
			new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

		// Assert
		verify(imgUtils).deleteImage(eq("Group Image"), eq("GROUP"));
		verify(imgUtils).saveImage(isA(MultipartFile.class), eq("GROUP"));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test:
	 * {@link GroupServiceImpl#update(UpdateGroupDTO, MultipartFile)}
	 */
	@Test
	void testUpdate2() throws IOException {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
		Optional<Group> ofResult = Optional.of(buildResult);
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
		doThrow(new NoSuchElementException("GROUP")).when(imgUtils)
			.deleteImage(Mockito.<String>any(), Mockito.<String>any());
		UpdateGroupDTO request = new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something");

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.update(request,
			new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
		verify(imgUtils).deleteImage(eq("Group Image"), eq("GROUP"));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test:
	 * {@link GroupServiceImpl#update(UpdateGroupDTO, MultipartFile)}
	 */
	@Test
	void testUpdate3() throws IOException {
		// Arrange
		Optional<Group> emptyResult = Optional.empty();
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
		UpdateGroupDTO request = new UpdateGroupDTO(1, "Name", 1, "The characteristics of someone or something");

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.update(request,
			new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#deleteById(int)}
	 */
	@Test
	void testDeleteById() {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
		Optional<Group> ofResult = Optional.of(buildResult);
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

		// Act
		groupServiceImpl.deleteById(1);

		// Assert
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#deleteById(int)}
	 */
	@Test
	void testDeleteById2() {
		// Arrange
		Optional<Group> emptyResult = Optional.empty();
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.deleteById(1));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#deleteById(int)}
	 */
	@Test
	void testDeleteById3() {
		// Arrange
		when(groupRepository.findById(Mockito.<Integer>any())).thenThrow(new NoSuchElementException("no such user"));

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.deleteById(1));
		verify(groupRepository).findById(eq(1));
	}

	/**
	 * Method under test: {@link GroupServiceImpl#save(Group)}
	 */
	@Test
	void testSave() {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Member leader = new Member();
		Group buildResult = groupImageResult.leader(leader).name("Name").password(1).build();
		when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);

		// Act
		Group actualSaveResult = groupServiceImpl.save(null);

		// Assert
		verify(groupRepository).save(isNull());
		assertEquals("Group Image", actualSaveResult.getGroupImage());
		assertEquals("Name", actualSaveResult.getName());
		assertEquals("The characteristics of someone or something", actualSaveResult.getDescription());
		assertNull(actualSaveResult.getId());
		assertNull(actualSaveResult.getCreatedAt());
		assertNull(actualSaveResult.getDeletedAt());
		assertEquals(1, actualSaveResult.getMemberCount());
		assertEquals(1, actualSaveResult.getPassword().intValue());
		assertFalse(actualSaveResult.isDeleted());
		List<GroupMember> groupMembers = actualSaveResult.getGroupMembers();
		assertTrue(groupMembers.isEmpty());
		assertEquals(groupMembers, groupServiceImpl.findAll());
		assertSame(leader, actualSaveResult.getLeader());
	}

	/**
	 * Method under test: {@link GroupServiceImpl#save(Group)}
	 */
	@Test
	void testSave2() {
		// Arrange
		when(groupRepository.save(Mockito.<Group>any())).thenThrow(new NoSuchElementException("foo"));

		// Act and Assert
		assertThrows(NoSuchElementException.class, () -> groupServiceImpl.save(null));
		verify(groupRepository).save(isNull());
	}
}
