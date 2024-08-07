package com.ssafy.a603.lingoland.openvidu;

import lombok.Builder;

@Builder
public record CustomTokenDto(String sessionId, String token) {
}
