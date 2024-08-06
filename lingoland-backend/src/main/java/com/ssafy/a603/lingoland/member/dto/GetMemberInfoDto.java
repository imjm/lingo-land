package com.ssafy.a603.lingoland.member.dto;

import com.ssafy.a603.lingoland.member.entity.Rank;
import lombok.Builder;

@Builder
public record GetMemberInfoDto(
        String nickname,
        String profileImage,
        long experiencePoint,
        String rank) {
}
