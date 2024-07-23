package com.ssafy.a603.lingoland.group.repository;

import static com.ssafy.a603.lingoland.group.entity.QGroup.*;
import static com.ssafy.a603.lingoland.group.entity.QGroupMember.*;
import static com.ssafy.a603.lingoland.member.entity.QMember.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.a603.lingoland.group.dto.MemberInGroupResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupCustomRepositoryImpl implements GroupCustomRepository {
	private final JPAQueryFactory queryFactory;

	public List<MemberInGroupResponseDTO> findAllMembresInGroup(int groupId, String keyword) {
		List<MemberInGroupResponseDTO> members = queryFactory.select(
				Projections.constructor(MemberInGroupResponseDTO.class,
					member.nickname,
					member.profileImage,
					groupMember.description,
					member.id.eq(group.leader.id).as("isLeader")
				))
			.from(member)
			.join(groupMember).on(member.id.eq(groupMember.id.memberId))
			.join(group).on(groupMember.id.groupId.eq(group.id))
			.where(
				applySoftDelete(),
				targetGroup(groupId),
				containsNickname(keyword)
			)
			.fetch();
		return moveLeaderToTop(members);
	}

	private List<MemberInGroupResponseDTO> moveLeaderToTop(List<MemberInGroupResponseDTO> members) {
		int leaderIndex = -1;

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).isLeader()) {
				leaderIndex = i;
				break;
			}
		}

		if (leaderIndex != -1 && leaderIndex != 0) {
			MemberInGroupResponseDTO leader = members.remove(leaderIndex);
			members.add(0, leader);
		}
		return members;
	}

	private BooleanExpression applySoftDelete() {
		return groupMember.isDeleted.eq(false);
	}

	private BooleanExpression targetGroup(int groupId) {
		return groupMember.group.id.eq(groupId);
	}

	private BooleanExpression containsNickname(String keyword) {
		return StringUtils.isNullOrEmpty(keyword) ? null : member.nickname.containsIgnoreCase(keyword);
	}
}
