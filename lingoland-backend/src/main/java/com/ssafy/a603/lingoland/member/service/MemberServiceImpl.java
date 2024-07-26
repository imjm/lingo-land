package com.ssafy.a603.lingoland.member.service;

import com.ssafy.a603.lingoland.member.dto.*;
import com.ssafy.a603.lingoland.member.entity.Role;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
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
public class MemberServiceImpl implements UserDetailsService, MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member saveNewMember(SignUpDto signUpRequest) {
        Member member = Member.builder()
                .loginId(signUpRequest.getLoginId())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .rank("temp")
                .role(Role.ROLE_USER)
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
                .profileImage(member.getProfileImage())
                .experiencePoint(member.getExperiencePoint())
                .build();
    }

    @Transactional
    public void updateNickname(UpdateNicknameDto updateNicknameDto, CustomUserDetails customUserDetails) {
        Member member = getMember(customUserDetails.getUsername());
        member.updateNickname(updateNicknameDto.nickname());
    }

    @Transactional
    @Override
    public void updatePassword(UpdatePasswordDto updatePasswordDto, CustomUserDetails customUserDetails) {
        Member member = getMember(customUserDetails.getUsername());
        member.updatePassword(passwordEncoder.encode(updatePasswordDto.password()));
    }

    @Transactional
    @Override
    public void updateProfileImage(UpdateProfileImageDto updateProfileImageDto, CustomUserDetails customUserDetails) {
        Member member = getMember(customUserDetails.getUsername());
        member.updateProfileImage(updateProfileImageDto.profileImage());
    }

    private Member getMember(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("존재하지 않은 유저입니다"));
    }

    public boolean checkIdDuplication(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }
}
