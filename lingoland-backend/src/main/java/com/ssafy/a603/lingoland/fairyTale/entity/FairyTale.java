package com.ssafy.a603.lingoland.fairyTale.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.util.ContentConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

	@Convert(converter = ContentConverter.class)
	@Column(columnDefinition = "jsonb")
	private Content content;

	@Column(name = "summary", length = 2000)
	private String summary;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "fairyTale")
	private List<FairyTaleMember> fairyTaleMembers = new ArrayList<>();

	protected FairyTale(Content content, String summary) {
		this.content = content;
		this.summary = summary;
	}

	@Getter
	public static class Content {
		private String title;
		private String cover;
		private List<Story> stories;

		@Builder
		protected Content(String title, String cover, List<Story> stories) {
			this.title = title;
			this.cover = cover;
			this.stories = stories;
		}

		@Getter
		public static class Story {
			private String illustration;
			private String story;

			@Builder
			protected Story(String illustration, String story) {
				this.illustration = illustration;
				this.story = story;
			}
		}
	}
}
