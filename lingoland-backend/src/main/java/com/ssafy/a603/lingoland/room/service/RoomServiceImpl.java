package com.ssafy.a603.lingoland.room.service;

import java.util.List;
import java.util.stream.Collectors;

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
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
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

	// 절 대 라이팅 룸 서비스 추가하지 마 . 순환 참조 조심해

	@Override
	@Transactional
	public void create(String sessionId, CustomUserDetails customUserDetails, String title) {
		FairyTale fairyTale = FairyTale.builder()
			.title(title)
			.build();
		FairyTale saved = fairyTaleRepository.save(fairyTale);

		Member member = findMemberByCustomUserDetails(customUserDetails);
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

	@Override
	@Transactional
	public void fairytaleStoryAdd(String sessionId, String starterLoginId, CustomUserDetails contributorDetails,
		FairyTale.Story story) {
		Room room = findRoomByRoomId(sessionId, starterLoginId);
		Member contributor = findMemberByCustomUserDetails(contributorDetails);
		room.addContributer(contributor);
		room.getFairyTale().addContent(story);
	}

	@Override
	@Transactional
	public void fairytaleComplete(String sessionId, String starterLoginId, String cover, String summary) {
		Room room = findRoomByRoomId(sessionId, starterLoginId);
		room.getFairyTale().completeBefore(cover, summary);
		room.getFairyTale().complete();
	}

	@Override
	@Transactional
	public void fairytaleInComplete(String sessionId, String starterLoginId) {
		Room room = findRoomByRoomId(sessionId, starterLoginId);
		room.getFairyTale().inComplete();
	}

	@Override
	@Transactional
	public void endRoom(String sessionId) {
		List<Room> rooms = roomRepository.findByIdSessionId(sessionId);
		rooms.forEach(Room::delete);
	}

	@Override
	public FairyTale findFairyTale(String sessionId, String loginId) {
		return findRoomByRoomId(sessionId, loginId).getFairyTale();
	}

	@Override
	public List<FairyTale> findFairyTalesInSession(String sessionId) {
		List<Room> rooms = roomRepository.findByIdSessionId(sessionId);
		return rooms.stream()
			.map(Room::getFairyTale)
			.collect(Collectors.toList());
	}

	private Room findRoomByRoomId(String sessionId, String starterLoginId) {
		Member member = memberRepository.findByLoginId(starterLoginId).orElseThrow(() -> {
			log.error("No such member id : {}", starterLoginId);
			return new NotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		});
		return roomRepository.findById(new RoomId(sessionId, member.getId())).orElseThrow(() -> {
			log.error("No such room");
			return new NotFoundException(ErrorCode.NOT_FOUND);
		});
	}

	private Member findMemberByCustomUserDetails(CustomUserDetails customUserDetails) {
		return memberRepository.findById(customUserDetails.getMemberId()).orElseThrow(() -> {
			log.error("No such member id : {}", customUserDetails.getMemberId());
			return new NotFoundException(ErrorCode.MEMBER_NOT_FOUND);
		});
	}
}
