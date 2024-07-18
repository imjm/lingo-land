package com.ssafy.a603.lingoland.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.util.ImgUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	private static final String GROUP_IMAGE_PATH = "GROUP";
	private final GroupRepository groupRepository;
	private final ImgUtils imgUtils;

	@Override
	public Group create(CreateGroupDTO request, MultipartFile groupImage) {
		Group group = Group.builder()
			.name(request.name())
			.password(request.password())
			.description(request.description())
			.build();

		String savePath = imgUtils.saveImage(groupImage, GROUP_IMAGE_PATH);
		group.setGroupImagePath(savePath);
		return groupRepository.save(group);
	}

	@Override
	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	public Group findById(int id) {
		return groupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No such group"));

	}

	@Override
	public void update(UpdateGroupDTO request, MultipartFile groupImage) {
		Group group = groupRepository.findById(request.id())
			.orElseThrow(() -> new NoSuchElementException("no such user"));
		group.updateUser(request);
		imgUtils.deleteImage(group.getGroupImage(), GROUP_IMAGE_PATH);
		imgUtils.saveImage(groupImage, GROUP_IMAGE_PATH);
	}

	@Override
	public void deleteById(int id) {
		// TODO : JWT 구현 이후 Security Context Holder에서 접속 유저 정보를 가져와 그룹장이면 허용하기
		Group group = groupRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("no such user"));
		group.delete();
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}
}
