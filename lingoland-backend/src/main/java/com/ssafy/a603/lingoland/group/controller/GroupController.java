package com.ssafy.a603.lingoland.group.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.service.GroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

	private final GroupService groupService;

	@PostMapping(produces = "application/json", consumes = "multipart/form-data")
	public ResponseEntity<?> createGroup(@RequestPart(value = "makeGroup") CreateGroupDTO createGroupDTO,
		@RequestPart(value = "groupImage", required = false) MultipartFile groupImage) {
		groupService.create(createGroupDTO, groupImage);
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
		@RequestPart(value = "makeGroup") UpdateGroupDTO updateGroupDTO,
		@RequestPart(value = "groupImage", required = false) MultipartFile groupImage) {
		groupService.update(updateGroupDTO, groupImage);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "/{groupsId}")
	public ResponseEntity<?> deleteGroup(@PathVariable Integer groupsId) {
		groupService.deleteById(groupsId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
