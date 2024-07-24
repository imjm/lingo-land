package com.ssafy.a603.lingoland.fairyTale.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public FairyTale createFairyTale(FairyTale.Content content, String summary, List<String> writers) {
		System.out.println("\n\n\n\n" + content.getTitle());
		System.out.println("\n\n\n\n" + content.getStories().getFirst().getStory());
		System.out.println("\n\n\n\n" + summary);

		List<Member> members = writers.stream()
			.map(memberLoginId -> memberRepository.findByLoginId(memberLoginId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberLoginId)))
			.toList();

		FairyTale fairyTale = FairyTale.builder()
			.content(content)
			.summary(summary)
			.build();

		for (Member member : members) {
			FairyTaleMember fairyTaleMember = FairyTaleMember.builder()
				.fairyTale(fairyTale)
				.member(member)
				.build();
			member.getFairyTaleMembers().add(fairyTaleMember);
			fairyTale.getFairyTaleMembers().add(fairyTaleMember);
			fairyTaleMemberRepository.save(fairyTaleMember);
		}

		return fairyTaleRepository.save(fairyTale);
	}
}
