package com.ssafy.a603.lingoland.group.repository;

import java.util.List;

import com.ssafy.a603.lingoland.group.dto.MemberInGroupResponseDTO;

public interface GroupCustomRepository {
	List<MemberInGroupResponseDTO> findAllMembresInGroup(int groupId, String keyword);
}
