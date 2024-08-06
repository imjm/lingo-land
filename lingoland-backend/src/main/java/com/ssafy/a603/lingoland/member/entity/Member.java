package com.ssafy.a603.lingoland.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMember;
import com.ssafy.a603.lingoland.global.entity.BaseEntity;
import com.ssafy.a603.lingoland.group.entity.GroupMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
	name = "MEMBER_SEQ_GENERATOR",
	sequenceName = "member_id_seq",
	allocationSize = 1
)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private int id;

	@Column(unique = true, nullable = false, length = 20)
	private String loginId;

	@Column(nullable = false, length = 20)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Column(columnDefinition = "TEXT")
	private String profileImage;

	@Column(nullable = false)
	private long experiencePoint;

	@Enumerated(EnumType.STRING)
	private Rank rank;

	private LocalDateTime deletedAt;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Column(nullable = false)
	private long runningPlayedCount;

	@Column(nullable = false)
	private long writingPlayedCount;

	@Column(columnDefinition = "TEXT")
	private String refreshToken;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<GroupMember> groupMembers = new ArrayList<>();

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FairyTaleMember> fairyTaleMembers = new ArrayList<>();

	public void updateRefreshToken(String refresh) {
		this.refreshToken = refresh;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public void updateExperiencePoint(long points) {
		this.experiencePoint += points;
		checkRankUp(points);
	}

	private void checkRankUp(long points) {
		long currentLevel = this.experiencePoint / 1000;
		long previousLevel = (this.experiencePoint - points) / 1000;
		if(currentLevel > previousLevel) {
			for(long i = previousLevel; i < currentLevel; i++) {
				this.rank = this.rank.nextRank();
			}
		}
	}
}
