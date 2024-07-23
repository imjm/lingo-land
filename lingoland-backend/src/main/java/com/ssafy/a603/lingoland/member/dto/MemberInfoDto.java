package com.ssafy.a603.lingoland.member.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record MemberInfoDto(String nickname, String profileImage, long experiencePoint) {
}
