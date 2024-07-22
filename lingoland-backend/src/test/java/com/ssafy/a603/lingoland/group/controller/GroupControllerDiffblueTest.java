package com.ssafy.a603.lingoland.group.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.entity.GroupMember;
import com.ssafy.a603.lingoland.group.entity.GroupMemberId;
import com.ssafy.a603.lingoland.group.repository.GroupMemberRepository;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.group.service.GroupService;
import com.ssafy.a603.lingoland.group.service.GroupServiceImpl;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.util.ImgUtils;

@ContextConfiguration(classes = {GroupController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GroupControllerDiffblueTest {
	@Autowired
	private GroupController groupController;

	@MockBean
	private GroupService groupService;

	/**
	 * Method under test:
	 * {@link GroupController#createGroup(CreateGroupDTO, MultipartFile)}
	 */
	@Test
	void testCreateGroup() throws IOException {
		try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
			//   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

			// Arrange
			mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
				.thenReturn(new ByteArrayOutputStream(1));
			GroupRepository groupRepository = mock(GroupRepository.class);
			Group.GroupBuilder groupImageResult = Group.builder()
				.description("The characteristics of someone or something")
				.groupImage("Group Image");
			Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
			when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);
			MemberRepository memberRepository = mock(MemberRepository.class);
			Optional<Member> ofResult = Optional.of(new Member());
			when(memberRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
			GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
			GroupMember.GroupMemberBuilder descriptionResult = GroupMember.builder()
				.description("The characteristics of someone or something");
			GroupMemberId id = GroupMemberId.builder().groupId(1).memberId(1).build();
			GroupMember buildResult2 = descriptionResult.id(id).build();
			when(groupMemberRepository.save(Mockito.<GroupMember>any())).thenReturn(buildResult2);
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, memberRepository, groupMemberRepository, new ImgUtils()));
			CreateGroupDTO createGroupDTO = new CreateGroupDTO("Name", 1, "The characteristics of someone or something",
				1);

			// Act
			ResponseEntity<?> actualCreateGroupResult = groupController.createGroup(createGroupDTO,
				new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

			// Assert
			mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
			verify(memberRepository).findById(eq(1));
			verify(groupRepository).save(isA(Group.class));
			verify(groupMemberRepository).save(isA(GroupMember.class));
			HttpStatusCode statusCode = actualCreateGroupResult.getStatusCode();
			assertTrue(statusCode instanceof HttpStatus);
			assertEquals("group made.", actualCreateGroupResult.getBody());
			assertEquals(201, actualCreateGroupResult.getStatusCodeValue());
			assertEquals(HttpStatus.CREATED, statusCode);
			assertTrue(actualCreateGroupResult.hasBody());
			HttpHeaders headers = actualCreateGroupResult.getHeaders();
			assertTrue(headers.isEmpty());
			assertEquals(headers, groupController.getGroups().getHeaders());
		}
	}

	/**
	 * Method under test:
	 * {@link GroupController#createGroup(CreateGroupDTO, MultipartFile, CustomUserDetails)}
	 */
	@Test
	void testCreateGroup2() throws IOException {
		try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
			//   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

			// Arrange
			mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
				.thenReturn(new ByteArrayOutputStream(1));

			Member leader = new Member();
			leader.updateRefreshToken("Refresh");
			Group buildResult = Group.builder()
				.description("The characteristics of someone or something")
				.groupImage("Group Image")
				.leader(leader)
				.name("Name")
				.password(1)
				.build();
			GroupRepository groupRepository = mock(GroupRepository.class);
			when(groupRepository.save(Mockito.<Group>any())).thenReturn(buildResult);

			Member member = new Member();
			member.updateRefreshToken("Refresh");
			Optional<Member> ofResult = Optional.of(member);
			MemberRepository memberRepository = mock(MemberRepository.class);
			when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult);
			GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
			GroupMember.GroupMemberBuilder descriptionResult = GroupMember.builder()
				.description("The characteristics of someone or something");
			GroupMemberId id = GroupMemberId.builder().groupId(1).memberId(1).build();
			GroupMember buildResult2 = descriptionResult.id(id).build();
			when(groupMemberRepository.save(Mockito.<GroupMember>any())).thenReturn(buildResult2);
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, memberRepository, groupMemberRepository, new ImgUtils()));
			CreateGroupDTO createGroupDTO = new CreateGroupDTO("Name", 1,
				"The characteristics of someone or something");

			MockMultipartFile groupImage = new MockMultipartFile("Name",
				new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

			// Act
			ResponseEntity<?> actualCreateGroupResult = groupController.createGroup(createGroupDTO, groupImage,
				new CustomUserDetails(new Member()));

			// Assert
			verify(memberRepository).findByLoginId(isNull());
			mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
			verify(groupRepository).save(isA(Group.class));
			verify(groupMemberRepository).save(isA(GroupMember.class));
			HttpStatusCode statusCode = actualCreateGroupResult.getStatusCode();
			assertTrue(statusCode instanceof HttpStatus);
			assertEquals("group made.", actualCreateGroupResult.getBody());
			assertEquals(201, actualCreateGroupResult.getStatusCodeValue());
			assertEquals(HttpStatus.CREATED, statusCode);
			assertTrue(actualCreateGroupResult.hasBody());
			HttpHeaders headers = actualCreateGroupResult.getHeaders();
			assertTrue(headers.isEmpty());
			assertEquals(headers, groupController.getGroups().getHeaders());
		}
	}

	/**
	 * Method under test: {@link GroupController#getGroupById(Integer)}
	 */
	@Test
	void testGetGroupById() {
		//   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

		// Arrange
		Member leader = new Member();
		leader.updateRefreshToken("Refresh");
		Group buildResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image")
			.leader(leader)
			.name("Name")
			.password(1)
			.build();
		Optional<Group> ofResult = Optional.of(buildResult);
		GroupRepository groupRepository = mock(GroupRepository.class);
		when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
		MemberRepository memberRepository = mock(MemberRepository.class);
		GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
		GroupController groupController = new GroupController(
			new GroupServiceImpl(groupRepository, memberRepository, groupMemberRepository, new ImgUtils()));

		// Act
		ResponseEntity<?> actualGroupById = groupController.getGroupById(1);

		// Assert
		verify(groupRepository).findById(eq(1));
		Object body = actualGroupById.getBody();
		assertTrue(body instanceof Group);
		HttpStatusCode statusCode = actualGroupById.getStatusCode();
		assertTrue(statusCode instanceof HttpStatus);
		assertEquals("Group Image", ((Group)body).getGroupImage());
		assertEquals("Name", ((Group)body).getName());
		assertEquals("The characteristics of someone or something", ((Group)body).getDescription());
		assertNull(((Group)body).getId());
		assertNull(((Group)body).getDeletedAt());
		assertEquals(0, ((Group)body).getMemberCount());
		assertEquals(1, ((Group)body).getPassword().intValue());
		assertEquals(200, actualGroupById.getStatusCodeValue());
		assertEquals(HttpStatus.OK, statusCode);
		assertFalse(((Group)body).isDeleted());
		List<GroupMember> groupMembers = ((Group)body).getGroupMembers();
		assertTrue(groupMembers.isEmpty());
		assertTrue(actualGroupById.hasBody());
		HttpHeaders headers = actualGroupById.getHeaders();
		assertTrue(headers.isEmpty());
		ResponseEntity<?> groups = groupController.getGroups();
		assertEquals(groupMembers, groups.getBody());
		assertEquals(headers, groups.getHeaders());
		assertSame(leader, ((Group)body).getLeader());
		assertSame(statusCode, groups.getStatusCode());
	}

	/**
	 * Method under test:
	 * {@link GroupController#deleteGroup(Integer, CustomUserDetails)}
	 */
	@Test
	void testDeleteGroup3() throws Exception {
		// Arrange
		doNothing().when(groupService).deleteById(anyInt(), Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}", 1);
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("customUserDetails",
			String.valueOf(new CustomUserDetails(new Member())));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test:
	 * {@link GroupController#deleteGroup(Integer, CustomUserDetails)}
	 */
	@Test
	void testDeleteGroup4() throws Exception {
		// Arrange
		doNothing().when(groupService).deleteById(anyInt(), Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}", 1);
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("customUserDetails", String.valueOf(""));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test: {@link GroupController#getGroupById(Integer)}
	 */
	@Test
	@Disabled("TODO: Complete this test")
	void testGetGroupById2() throws Exception {
		// TODO: Diffblue Cover was only able to create a partial test for this method:
		//   Diffblue AI was unable to find a test

		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");

		Member leader = new Member();
		leader.updateRefreshToken("Refresh");
		Group buildResult = groupImageResult.leader(leader).name("Name").password(1).build();
		when(groupService.findById(anyInt())).thenReturn(buildResult);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/groups/{groupsId}", 1);

		// Act and Assert
		MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content()
				.string(
					"{\"id\":null,\"name\":\"Name\",\"password\":1,\"description\":\"The characteristics of someone or something\","
						+ "\"memberCount\":0,\"groupImage\":\"Group Image\",\"createdAt\":[2024,7,22,16,45,45,173911800],\"deletedAt\":null"
						+ ",\"leader\":{\"id\":0,\"loginId\":null,\"nickname\":null,\"password\":null,\"profile_image\":null,\"experiencePoint"
						+ "\":0,\"rank\":null,\"createdAt\":null,\"deletedAt\":null,\"runningPlayedCount\":0,\"writingPlayedCount\":0,"
						+ "\"refreshToken\":\"Refresh\",\"groupMembers\":[],\"deleted\":false},\"groupMembers\":[],\"deleted\":false}"));
	}

	/**
	 * Method under test:
	 * {@link GroupController#joinGroup(Integer, JoinGroupRequestDTO, CustomUserDetails)}
	 */
	@Test
	void testJoinGroup2() throws Exception {
		// Arrange
		doNothing().when(groupService)
			.addMemberToGroupWithPasswordCheck(anyInt(), Mockito.<JoinGroupRequestDTO>any(),
				Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/groups/{groupsId}/users", 1);
		MockHttpServletRequestBuilder contentTypeResult = postResult
			.param("customUserDetails", String.valueOf(new CustomUserDetails(new Member())))
			.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
			objectMapper.writeValueAsString(new JoinGroupRequestDTO("The characteristics of someone or something", 1)));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test:
	 * {@link GroupController#joinGroup(Integer, JoinGroupRequestDTO, CustomUserDetails)}
	 */
	@Test
	void testJoinGroup3() throws Exception {
		// Arrange
		doNothing().when(groupService)
			.addMemberToGroupWithPasswordCheck(anyInt(), Mockito.<JoinGroupRequestDTO>any(),
				Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/groups/{groupsId}/users", 1);
		MockHttpServletRequestBuilder contentTypeResult = postResult.param("customUserDetails", String.valueOf(""))
			.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
			objectMapper.writeValueAsString(new JoinGroupRequestDTO("The characteristics of someone or something", 1)));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test:
	 * {@link GroupController#quitGroup(Integer, CustomUserDetails)}
	 */
	@Test
	void testQuitGroup() throws Exception {
		// Arrange
		doNothing().when(groupService).removeMemberFromGroup(anyInt(), Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}/users",
			1);
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("customUserDetails",
			String.valueOf(new CustomUserDetails(new Member())));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test:
	 * {@link GroupController#quitGroup(Integer, CustomUserDetails)}
	 */
	@Test
	void testQuitGroup2() throws Exception {
		// Arrange
		doNothing().when(groupService).removeMemberFromGroup(anyInt(), Mockito.<CustomUserDetails>any());
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}/users",
			1);
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("customUserDetails", String.valueOf(""));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test:
	 * {@link GroupController#updateGroupInfo(Integer, UpdateGroupDTO, MultipartFile)}
	 */
	@Test
	void testUpdateGroupInfo() throws IOException {
		try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
			//   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

			// Arrange
			mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
				.thenReturn(new ByteArrayOutputStream(1));
			mockFiles.when(() -> Files.delete(Mockito.<Path>any())).thenAnswer(invocation -> null);
			GroupRepository groupRepository = mock(GroupRepository.class);
			Group.GroupBuilder groupImageResult = Group.builder()
				.description("The characteristics of someone or something")
				.groupImage("Group Image");
			Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
			Optional<Group> ofResult = Optional.of(buildResult);
			when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
			MemberRepository memberRepository = mock(MemberRepository.class);
			GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, memberRepository, groupMemberRepository, new ImgUtils()));
			UpdateGroupDTO updateGroupDTO = new UpdateGroupDTO(1, "Name", 1,
				"The characteristics of someone or something");

			// Act
			ResponseEntity<?> actualUpdateGroupInfoResult = groupController.updateGroupInfo(1, updateGroupDTO,
				new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

			// Assert
			mockFiles.verify(() -> Files.delete(Mockito.<Path>any()));
			mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
			verify(groupRepository).findById(eq(1));
			HttpStatusCode statusCode = actualUpdateGroupInfoResult.getStatusCode();
			assertTrue(statusCode instanceof HttpStatus);
			assertNull(actualUpdateGroupInfoResult.getBody());
			assertEquals(204, actualUpdateGroupInfoResult.getStatusCodeValue());
			assertEquals(HttpStatus.NO_CONTENT, statusCode);
			assertFalse(actualUpdateGroupInfoResult.hasBody());
			HttpHeaders headers = actualUpdateGroupInfoResult.getHeaders();
			assertTrue(headers.isEmpty());
			assertEquals(headers, groupController.getGroups().getHeaders());
		}
	}

	/**
	 * Method under test:
	 * {@link GroupController#updateGroupInfo(Integer, UpdateGroupDTO, MultipartFile, CustomUserDetails)}
	 */
	@Test
	void testUpdateGroupInfo2() throws IOException {
		try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
			//   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

			// Arrange
			mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
				.thenReturn(new ByteArrayOutputStream(1));
			mockFiles.when(() -> Files.delete(Mockito.<Path>any())).thenAnswer(invocation -> null);

			Member leader = new Member();
			leader.updateRefreshToken("Refresh");
			Group buildResult = Group.builder()
				.description("The characteristics of someone or something")
				.groupImage("Group Image")
				.leader(leader)
				.name("Name")
				.password(1)
				.build();
			Optional<Group> ofResult = Optional.of(buildResult);
			GroupRepository groupRepository = mock(GroupRepository.class);
			when(groupRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

			Member member = new Member();
			member.updateRefreshToken("Refresh");
			Optional<Member> ofResult2 = Optional.of(member);
			MemberRepository memberRepository = mock(MemberRepository.class);
			when(memberRepository.findByLoginId(Mockito.<String>any())).thenReturn(ofResult2);
			GroupMemberRepository groupMemberRepository = mock(GroupMemberRepository.class);
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, memberRepository, groupMemberRepository, new ImgUtils()));
			UpdateGroupDTO updateGroupDTO = new UpdateGroupDTO(1, "Name", 1,
				"The characteristics of someone or something");

			MockMultipartFile groupImage = new MockMultipartFile("Name",
				new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

			// Act
			ResponseEntity<?> actualUpdateGroupInfoResult = groupController.updateGroupInfo(1, updateGroupDTO,
				groupImage,
				new CustomUserDetails(new Member()));

			// Assert
			verify(memberRepository).findByLoginId(isNull());
			mockFiles.verify(() -> Files.delete(Mockito.<Path>any()));
			mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
			verify(groupRepository).findById(eq(1));
			HttpStatusCode statusCode = actualUpdateGroupInfoResult.getStatusCode();
			assertTrue(statusCode instanceof HttpStatus);
			assertNull(actualUpdateGroupInfoResult.getBody());
			assertEquals(204, actualUpdateGroupInfoResult.getStatusCodeValue());
			assertEquals(HttpStatus.NO_CONTENT, statusCode);
			assertFalse(actualUpdateGroupInfoResult.hasBody());
			HttpHeaders headers = actualUpdateGroupInfoResult.getHeaders();
			assertTrue(headers.isEmpty());
			assertEquals(headers, groupController.getGroups().getHeaders());
		}
	}

	/**
	 * Method under test: {@link GroupController#deleteGroup(Integer)}
	 */
	@Test
	void testDeleteGroup() throws Exception {
		// Arrange
		doNothing().when(groupService).deleteById(anyInt());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}", 1);

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test: {@link GroupController#deleteGroup(Integer)}
	 */
	@Test
	void testDeleteGroup2() throws Exception {
		// Arrange
		doNothing().when(groupService).deleteById(anyInt());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/groups/{groupsId}", 1);
		requestBuilder.contentType("https://example.org/example");

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	/**
	 * Method under test: {@link GroupController#getGroups()}
	 */
	@Test
	void testGetGroups() throws Exception {
		// Arrange
		when(groupService.findAll()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/groups");

		// Act and Assert
		MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
			.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test:
	 * {@link GroupController#joinGroup(Integer, Integer, JoinGroupRequestDTO)}
	 */
	@Test
	void testJoinGroup() throws Exception {
		// Arrange
		doNothing().when(groupService)
			.addMemberToGroupWithPasswordCheck(anyInt(), anyInt(), Mockito.<JoinGroupRequestDTO>any());
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
			.post("/api/v1/groups/{groupsId}/users/{userId}", 1, 1)
			.contentType(MediaType.APPLICATION_JSON);

		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
			objectMapper.writeValueAsString(new JoinGroupRequestDTO("The characteristics of someone or something", 1)));

		// Act
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(groupController)
			.build()
			.perform(requestBuilder);

		// Assert
		actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
