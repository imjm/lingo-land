package com.ssafy.a603.lingoland.fairyTale.dto;

import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;

public record CreateFairyTaleRequestDTO(FairyTale.Content content, String summary, List<String> writers) {
}
