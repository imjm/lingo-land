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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
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

	@GetMapping("/{groupsId}")
	public ResponseEntity<?> getGroupById(@PathVariable Integer groupsId) {
		Group group = groupService.findById(groupsId);
		return ResponseEntity.status(HttpStatus.OK).body(group);
	}

	@GetMapping
	public ResponseEntity<?> getGroups() {
		List<Group> groups = groupService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(groups);
	}

	@PutMapping(path = "/{groupsId}", produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<?> updateGroupInfo(@PathVariable Integer groupsId,
		@RequestPart(value = "updateGroup") UpdateGroupDTO updateGroupDTO,
		@RequestPart(value = "groupImage", required = false) MultipartFile groupImage,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.update(groupsId, updateGroupDTO, groupImage, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "/{groupsId}")
	public ResponseEntity<?> deleteGroup(@PathVariable Integer groupsId,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.deleteById(groupsId, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/{groupsId}/users")
	public ResponseEntity<?> joinGroup(@PathVariable("groupsId") Integer groupsId,
		@RequestBody JoinGroupRequestDTO joinGroupRequestDTO,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.addMemberToGroupWithPasswordCheck(groupsId, joinGroupRequestDTO, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{groupsId}/users")
	public ResponseEntity<?> quitGroup(@PathVariable("groupsId") Integer groupsId,
		@CurrentUser CustomUserDetails customUserDetails) {
		groupService.removeMemberFromGroup(groupsId, customUserDetails);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
