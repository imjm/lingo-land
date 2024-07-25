package com.ssafy.a603.lingoland.fairyTale.service;

import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;

public interface FairyTaleService {
	public FairyTale createFairyTale(String title, String cover, String summary, List<FairyTale.Story> content,
		List<String> writers);
}
