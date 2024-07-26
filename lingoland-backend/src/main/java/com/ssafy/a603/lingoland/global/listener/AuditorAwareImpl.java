package com.ssafy.a603.lingoland.global.listener;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;

@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Optional<Integer> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		String loginId = ((CustomUserDetails)authentication.getPrincipal()).getUsername();
		return Optional.of(memberRepository.findByLoginId(loginId).get().getId());
	}
}
