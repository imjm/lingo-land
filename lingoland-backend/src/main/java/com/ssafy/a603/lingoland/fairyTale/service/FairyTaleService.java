package com.ssafy.a603.lingoland.fairyTale.service;

import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.dto.FairyTaleListResponseDTO;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;

public interface FairyTaleService {
	public FairyTale createFairyTale(String title, String cover, String summary, List<FairyTale.Story> content,
		List<String> writers);

	public List<FairyTaleListResponseDTO> findFairyTaleListByLoginId(CustomUserDetails customUserDetails);

	public FairyTale findFairyTaleById(Integer fairyTaleId);

	public void fairyTaleInvisible(Integer fairyTaleId, CustomUserDetails customUserDetails);
}
