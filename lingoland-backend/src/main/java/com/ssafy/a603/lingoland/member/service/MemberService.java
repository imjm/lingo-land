package com.ssafy.a603.lingoland.member.service;

import com.ssafy.a603.lingoland.member.dto.*;
import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;

public interface MemberService {

    Member saveNewMember(SignUpDto signUpRequest);

    void addRefreshToken(String loginId, String refresh);

    void deleteRefreshToken(String loginId);

    Boolean checkExistRefreshToken(String loginId);

    GetMemberInfoDto getMemberInfo(CustomUserDetails customUserDetails);

    void updateNickname(UpdateNicknameDto updateNicknameDto, CustomUserDetails customUserDetails);

    boolean checkIdDuplication(String loginId);

    void updatePassword(UpdatePasswordDto updatePasswordDto, CustomUserDetails customUserDetails);

    void updateProfileImage(UpdateProfileImageDto updateProfileImageDto, CustomUserDetails customUserDetails);

    GetMemberInfoDto getMemberInfoByLoginId(String loginId);
}
