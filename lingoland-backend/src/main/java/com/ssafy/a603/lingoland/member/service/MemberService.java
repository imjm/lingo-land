package com.ssafy.a603.lingoland.member.service;

import com.ssafy.a603.lingoland.member.dto.GetMemberInfoDto;
import com.ssafy.a603.lingoland.member.dto.UpdateNicknameDto;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.dto.SignUpDto;
import com.ssafy.a603.lingoland.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

<<<<<<< lingoland-backend/src/main/java/com/ssafy/a603/lingoland/member/service/MemberService.java
    @Transactional
    public Member saveNewMember(SignUpDto signUpRequest) {
        Member member = Member.builder()
                .loginId(signUpRequest.getLoginId())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .rank("d")
                .build();
        return memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = getMember(loginId);
        return new CustomUserDetails(member);
    }

    @Transactional
    public void addRefreshToken(String loginId, String refresh) {
        Member member = getMember(loginId);
        member.updateRefreshToken(refresh);
    }

    @Transactional
    public void deleteRefreshToken(String loginId) {
        Member member = getMember(loginId);
        member.updateRefreshToken(null);
    }

    public Boolean checkExistRefreshToken(String loginId) {
        Member member = getMember(loginId);
        return member.getRefreshToken() != null;
    }

    public GetMemberInfoDto getMemberInfo(CustomUserDetails customUserDetails) {
        Member member = getMember(customUserDetails.getUsername());
        return GetMemberInfoDto.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfile_image())
                .experiencePoint(member.getExperiencePoint())
                .build();
    }

    @Transactional
    public void updateNickname(UpdateNicknameDto updateNicknameDto, CustomUserDetails customUserDetails) {
        Member member = getMember(customUserDetails.getUsername());
        member.updateNickname(updateNicknameDto.nickname());
    }

    private Member getMember(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
    }
=======
	@Transactional
	public Member saveNewMember(SignUpRequest signUpRequest) {
		Member member = Member.builder()
			.loginId(signUpRequest.getLoginId())
			.nickname(signUpRequest.getNickname())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.createdAt(LocalDateTime.now())
			.rank("d")
			.build();
		return memberRepository.save(member);
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
		return new CustomUserDetails(member);
	}

	@Transactional
	public void addRefreshToken(String loginId, String refresh) {
		Member member = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
		member.updateRefreshToken(refresh);
	}

	@Transactional
	public void deleteRefreshToken(String loginId) {
		Member member = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
		member.updateRefreshToken(null);
	}

	public Boolean checkExistRefreshToken(String loginId) {
		Member member = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
		return member.getRefreshToken() != null;
	}

	public MemberInfoDto getMemberInfo(CustomUserDetails customUserDetails) {
		Member member = memberRepository.findByLoginId(customUserDetails.getUsername())
			.orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다."));
		return MemberInfoDto.builder()
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.experiencePoint(member.getExperiencePoint())
			.build();
	}
>>>>>>> lingoland-backend/src/main/java/com/ssafy/a603/lingoland/member/service/MemberService.java
}
