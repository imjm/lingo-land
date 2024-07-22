package com.ssafy.a603.lingoland.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.entity.GroupMember;
import com.ssafy.a603.lingoland.group.entity.GroupMemberId;
import com.ssafy.a603.lingoland.group.repository.GroupMemberRepository;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.util.ImgUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	private static final String GROUP_IMAGE_PATH = "GROUP";
	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final ImgUtils imgUtils;

	@Override
	public Group create(CreateGroupDTO request, MultipartFile groupImage) {
		Member member = memberRepository.findById(request.leaderId()).get();
		Group group = Group.builder()
			.name(request.name())
			.password(request.password())
			.description(request.description())
			.leader(member)
			.build();

		String savePath = imgUtils.saveImage(groupImage, GROUP_IMAGE_PATH);
		group.setGroupImagePath(savePath);
		Group createdGroup = groupRepository.save(group);

		addMemberToGroup(group, member, "그룹장 입니다.");

		return createdGroup;
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
			.orElseThrow(() -> new NoSuchElementException("no such member"));
		group.updateGroup(request);
		imgUtils.deleteImage(group.getGroupImage(), GROUP_IMAGE_PATH);
		imgUtils.saveImage(groupImage, GROUP_IMAGE_PATH);
	}

	@Override
	public void deleteById(int id) {
		// TODO : JWT 구현 이후 Security Context Holder에서 접속 유저 정보를 가져와 그룹장이면 허용하기
		Group group = groupRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("no such member"));
		group.delete();
	}

	@Override
	public void addMemberToGroupWithPasswordCheck(int groupsId, int memberId, JoinGroupRequestDTO joinGroupRequestDTO) {
		Group group = findById(groupsId);

		if (group.getPassword().intValue() != joinGroupRequestDTO.password().intValue()) {
			throw new RuntimeException("password not equals");
		}

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("no such member"));

		addMemberToGroup(group, member, joinGroupRequestDTO.description());

	}

	@Override
	@Transactional
	public void removeMemberFromGroup(int groupsId, int memberId) {
		GroupMemberId groupMemberId = GroupMemberId.builder()
			.groupId(groupsId)
			.memberId(memberId)
			.build();
		GroupMember groupMember = groupMemberRepository.findById(groupMemberId)
			.orElseThrow(() -> new NoSuchElementException("no such connection"));
		groupMember.quit();
		Group group = groupRepository.findById(groupsId).get();
		group.quit();
	}

	private void addMemberToGroup(Group group, Member member, String description) {
		GroupMemberId groupMemberId = GroupMemberId.builder()
			.groupId(group.getId())
			.memberId(member.getId())
			.build();

		GroupMember groupMember = GroupMember.builder()
			.id(groupMemberId)
			.description(description)
			.build();
		groupMember.addGroup(group);
		groupMember.addMember(member);

		group.join(groupMember);
		member.getGroupMembers().add(groupMember);

		groupMemberRepository.save(groupMember);
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}
}
