package com.ssafy.a603.lingoland.group.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a603.lingoland.group.dto.CreateGroupDTO;
import com.ssafy.a603.lingoland.group.dto.JoinGroupRequestDTO;
import com.ssafy.a603.lingoland.group.dto.MemberInGroupResponseDTO;
import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.group.entity.Group;
import com.ssafy.a603.lingoland.group.entity.GroupMember;
import com.ssafy.a603.lingoland.group.entity.GroupMemberId;
import com.ssafy.a603.lingoland.group.repository.GroupMemberRepository;
import com.ssafy.a603.lingoland.group.repository.GroupRepository;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
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
	@Transactional
	public Group create(CreateGroupDTO request, MultipartFile groupImage, CustomUserDetails customUserDetails) {
		Member member = getMemberFromUserDetails(customUserDetails);
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
	@Transactional(readOnly = true)
	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Group findById(int id) {
		return groupRepository.findById(id).orElseThrow(
			() -> new NoSuchElementException("No such group")
		);
	}

	@Override
	@Transactional
	public void update(Integer groupId, UpdateGroupDTO request, MultipartFile groupImage,
		CustomUserDetails customUserDetails) {
		Member member = getMemberFromUserDetails(customUserDetails);
		Group group = findById(request.id());

		if (group.getLeader().getId() != member.getId()) {
			throw new RuntimeException("권한 없음");
		}

		group.updateGroup(request);
		imgUtils.deleteImage(group.getGroupImage(), GROUP_IMAGE_PATH);
		imgUtils.saveImage(groupImage, GROUP_IMAGE_PATH);
	}

	@Override
	@Transactional
	public void deleteById(int id, CustomUserDetails customUserDetails) {
		Member member = getMemberFromUserDetails(customUserDetails);
		Group group = findById(id);
		if (group.getLeader().getId() != member.getId()) {
			throw new RuntimeException("권한 없음");
		}
		group.delete();
	}

	@Override
	@Transactional
	public void addMemberToGroupWithPasswordCheck(int groupId, JoinGroupRequestDTO joinGroupRequestDTO,
		CustomUserDetails customUserDetails) {
		Group group = findById(groupId);

		if (group.getPassword().intValue() != joinGroupRequestDTO.password().intValue()) {
			throw new RuntimeException("password not equals");
		}

		Member member = getMemberFromUserDetails(customUserDetails);

		addMemberToGroup(group, member, joinGroupRequestDTO.description());

	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberInGroupResponseDTO> findAllMembersByGroupId(int groupId, String keyword,
		CustomUserDetails customUserDetails) {
		Member member = getMemberFromUserDetails(customUserDetails);
		//내가 속한 그룹이 맞는가?
		if (!isGroupMember(member, groupId)) {
			throw new RuntimeException("Not my group");
		}

		return groupRepository.findAllMembresInGroup(groupId, keyword);
	}

	private boolean isGroupMember(Member member, int groupId) {
		for (GroupMember groupMember : member.getGroupMembers()) {
			if (groupMember.getGroup().getId() == groupId)
				return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void removeMemberFromGroup(int groupId, CustomUserDetails customUserDetails) {
		Member member = getMemberFromUserDetails(customUserDetails);

		GroupMemberId groupMemberId = GroupMemberId.builder()
			.groupId(groupId)
			.memberId(member.getId())
			.build();
		GroupMember groupMember = groupMemberRepository.findById(groupMemberId)
			.orElseThrow(() -> new NoSuchElementException("no such connection"));
		groupMember.quit();
		Group group = findById(groupId);
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

		group.join();

		groupMemberRepository.save(groupMember);
	}

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	private Member getMemberFromUserDetails(CustomUserDetails customUserDetails) {
		String loginId = customUserDetails.getUsername();
		return memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new NoSuchElementException("no such member"));
	}
}
