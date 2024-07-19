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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.group.service.GroupService;
import com.ssafy.a603.lingoland.group.service.GroupServiceImpl;
import com.ssafy.a603.lingoland.member.entity.Member;
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
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, new ImgUtils()));
			CreateGroupDTO createGroupDTO = new CreateGroupDTO("Name", 1, "The characteristics of someone or something",
				1);

			// Act
			ResponseEntity<?> actualCreateGroupResult = groupController.createGroup(createGroupDTO,
				new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

			// Assert
			mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
			verify(groupRepository).save(isA(Group.class));
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
			GroupController groupController = new GroupController(
				new GroupServiceImpl(groupRepository, new ImgUtils()));
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
	 * Method under test: {@link GroupController#getGroupById(Integer)}
	 */
	@Test
	void testGetGroupById() throws Exception {
		// Arrange
		Group.GroupBuilder groupImageResult = Group.builder()
			.description("The characteristics of someone or something")
			.groupImage("Group Image");
		Group buildResult = groupImageResult.leader(new Member()).name("Name").password(1).build();
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
						+ "\"memberCount\":1,\"groupImage\":\"Group Image\",\"createdAt\":null,\"deletedAt\":null,\"leader\":{\"id\":0,\"loginId"
						+ "\":null,\"nickname\":null,\"password\":null,\"profile_image\":null,\"experiencePoint\":0,\"rank\":null,\"createdAt"
						+ "\":null,\"deletedAt\":null,\"runningPlayedCount\":0,\"writingPlayedCount\":0,\"refreshToken\":null,\"deleted\""
						+ ":false},\"groupMembers\":[],\"deleted\":false}"));
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
}
