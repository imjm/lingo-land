package com.ssafy.a603.lingoland.fairyTale.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMember;
import com.ssafy.a603.lingoland.fairyTale.repository.FairyTaleMemberRepository;
import com.ssafy.a603.lingoland.fairyTale.repository.FairyTaleRepository;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FairyTaleServiceImpl implements FairyTaleService {
	private final FairyTaleRepository fairyTaleRepository;
	private final MemberRepository memberRepository;
	private final FairyTaleMemberRepository fairyTaleMemberRepository;

	@Override
	@Transactional
	public FairyTale createFairyTale(String title, String cover, String summary, List<FairyTale.Story> content,
		List<String> writers) {
		List<Member> members = writers.stream()
			.map(memberLoginId -> memberRepository.findByLoginId(memberLoginId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberLoginId)))
			.toList();

		FairyTale fairyTale = fairyTaleRepository.save(FairyTale.builder()
			.title(title)
			.cover(cover)
			.summary(summary)
			.content(content)
			.build());
		for (Member member : members) {
			FairyTaleMember fairyTaleMember = FairyTaleMember.builder()
				.fairyTale(fairyTale)
				.member(member)
				.build();
			fairyTaleMemberRepository.save(fairyTaleMember);
		}
		return fairyTale;
	}
}
