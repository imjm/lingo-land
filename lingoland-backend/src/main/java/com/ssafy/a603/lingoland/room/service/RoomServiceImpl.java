package com.ssafy.a603.lingoland.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMember;
import com.ssafy.a603.lingoland.fairyTale.repository.FairyTaleMemberRepository;
import com.ssafy.a603.lingoland.fairyTale.repository.FairyTaleRepository;
import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;
import com.ssafy.a603.lingoland.global.error.exception.NotFoundException;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.room.entity.Room;
import com.ssafy.a603.lingoland.room.entity.RoomId;
import com.ssafy.a603.lingoland.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomRepository roomRepository;
	private final FairyTaleRepository fairyTaleRepository;
	private final FairyTaleMemberRepository fairyTaleMemberRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void create(String sessionId, Member member, String title) {
		FairyTale fairyTale = FairyTale.builder()
			.title(title)
			.build();

		FairyTale saved = fairyTaleRepository.save(fairyTale);
		fairyTaleMemberRepository.save(
			FairyTaleMember.builder()
				.fairyTale(saved)
				.member(member)
				.build()
		);

		Room room = Room.builder()
			.sessionId(sessionId)
			.starter(member)
			.build();
		room.setFairyTale(saved);
		roomRepository.save(room);
	}

	//session id, 키값, 현재 작성 멤버, 만들어진
	// 동화 한 페이지
	@Transactional
	public void fairytaleUpdate(String sessionId, String starterLoginId, Member member, FairyTale.Story story) {
		Room room = findByRoomId(sessionId, starterLoginId);
		room.addContributer(member);
		room.getFairyTale().addContent(story);
	}

	private Room findByRoomId(String sessionId, String starterLoginId) {
		Member member = memberRepository.findByLoginId(starterLoginId).orElseThrow(() -> {
			log.error("No such member id : {}", starterLoginId);
			return new NotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		});
		return roomRepository.findById(new RoomId(sessionId, member.getId())).orElseThrow(() -> {
			log.error("No such room");
			return new NotFoundException(ErrorCode.NOT_FOUND);
		});
	}
}
