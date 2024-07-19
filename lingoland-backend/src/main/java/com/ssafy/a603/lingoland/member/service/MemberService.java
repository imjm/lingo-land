package com.ssafy.a603.lingoland.member.service;

import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.dto.SignUpRequest;
import com.ssafy.a603.lingoland.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
        Member member = memberRepository.findByLoginId(loginId);

        if (member != null) {
            return new CustomUserDetails(member);
        }
        return null;
    }

    @Transactional
    public void addRefreshToken(String loginId, String refresh) {
        Member member = memberRepository.findByLoginId(loginId);
        member.updateRefreshToken(refresh);
    }

    @Transactional
    public void deleteRefreshToken(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        member.updateRefreshToken(null);
    }

    public Boolean checkExistRefreshToken(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        return member != null;
    }
}
