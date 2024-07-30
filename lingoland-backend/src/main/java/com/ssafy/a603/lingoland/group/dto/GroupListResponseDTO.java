package com.ssafy.a603.lingoland.group.dto;

import lombok.Builder;

@Builder
public record GroupListResponseDTO(Integer id, String name, String description) {
}
