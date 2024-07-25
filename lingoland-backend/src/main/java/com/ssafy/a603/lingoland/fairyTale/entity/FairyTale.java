package com.ssafy.a603.lingoland.fairyTale.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FairyTale {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fairy_tale_seq")
	@SequenceGenerator(name = "fairy_tale_seq", sequenceName = "fairy_tale_id_seq", allocationSize = 1)
	private Integer id;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Content content;

	@Column(name = "summary", length = 2000)
	private String summary;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "fairyTale")
	private List<FairyTaleMember> fairyTaleMembers = new ArrayList<>();

	@Builder
	protected FairyTale(Content content, String summary) {
		this.content = content;
		this.summary = summary;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Content {
		private String title;
		private String cover;
		private List<Story> stories;

		@Builder
		protected Content(@JsonProperty("title") String title,
			@JsonProperty("cover") String cover,
			@JsonProperty("stories") List<Story> stories) {
			this.title = title;
			this.cover = cover;
			this.stories = stories;
		}

		@Getter
		@NoArgsConstructor(access = AccessLevel.PROTECTED)
		public static class Story {
			private String illustration;
			private String story;

			@Builder
			protected Story(@JsonProperty("illustration") String illustration,
				@JsonProperty("story") String story) {
				this.illustration = illustration;
				this.story = story;
			}
		}
	}
}
