package com.ssafy.a603.lingoland.fairyTale.service;

import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;

public interface FairyTaleService {
	public FairyTale createFairyTale(FairyTale.Content content, String summary, List<String> writers);
}
