package com.ssafy.a603.lingoland.group.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;

public interface GroupService {
	Group create(CreateGroupDTO request, MultipartFile groupImage);

	List<Group> findAll();

	Group findById(int id);

	void update(UpdateGroupDTO request, MultipartFile groupImage);

	void deleteById(int id);

	Group save(Group group);
}
