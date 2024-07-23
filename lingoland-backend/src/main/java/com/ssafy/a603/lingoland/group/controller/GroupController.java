package com.ssafy.a603.lingoland.group.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
import com.ssafy.a603.lingoland.group.dto.MemberInGroupResponseDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.service.GroupService;
import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

	private final GroupService groupService;

	@PostMapping(produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<?> createGroup(@RequestPart(value = "createGroup") CreateGroupDTO createGroupDTO,
		@RequestPart(value = "groupImage", required = false) MultipartFile groupImage,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.create(createGroupDTO, groupImage, customUserDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body("group made.");
	}

	@GetMapping("/{groupId}")
	public ResponseEntity<?> getGroupById(@PathVariable Integer groupId) {
		Group group = groupService.findById(groupId);
		return ResponseEntity.status(HttpStatus.OK).body(group);
	}

	@GetMapping
	public ResponseEntity<?> getGroups() {
		List<Group> groups = groupService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(groups);
	}

	@PutMapping(path = "/{groupId}", produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<?> updateGroupInfo(@PathVariable Integer groupId,
		@RequestPart(value = "updateGroup") UpdateGroupDTO updateGroupDTO,
		@RequestPart(value = "groupImage", required = false) MultipartFile groupImage,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.update(groupId, updateGroupDTO, groupImage, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "/{groupId}")
	public ResponseEntity<?> deleteGroup(@PathVariable Integer groupId,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.deleteById(groupId, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/{groupId}/users")
	public ResponseEntity<?> joinGroup(@PathVariable("groupId") Integer groupId,
		@RequestBody JoinGroupRequestDTO joinGroupRequestDTO,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.addMemberToGroupWithPasswordCheck(groupId, joinGroupRequestDTO, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{groupId}/users")
	public ResponseEntity<?> findMembersInGroup(@PathVariable("groupId") Integer groupId,
		@RequestParam(required = false, name = "keyword") String keyword,
		@CurrentUser CustomUserDetails customUserDetails) {
		List<MemberInGroupResponseDTO> members = groupService.findAllMembersByGroupId(groupId, keyword,
			customUserDetails);
		return ResponseEntity.status(HttpStatus.OK).body(members);
	}

	@DeleteMapping("/{groupId}/users")
	public ResponseEntity<?> quitGroup(@PathVariable("groupId") Integer groupId,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.removeMemberFromGroup(groupId, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
