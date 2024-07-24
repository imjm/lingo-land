package com.ssafy.a603.lingoland.fairyTale.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter(autoApply = true)
@Slf4j
public class ContentConverter implements AttributeConverter<FairyTale.Content, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(FairyTale.Content content) {
		try {
			return content != null ? objectMapper.writeValueAsString(content) : null;
		} catch (JsonProcessingException e) {
			log.error("Error Convert FairyTale Content JSON TO String\n" + e.toString());
			throw new IllegalArgumentException("Error Convert FairyTale Content JSON TO String", e);
		}
	}

	@Override
	public FairyTale.Content convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, FairyTale.Content.class);
		} catch (IOException e) {
			log.error("Error reading JSON string to FairyTale Content\n" + e.toString());
			throw new IllegalArgumentException("Error reading JSON string to DataA", e);
		}
	}
}
