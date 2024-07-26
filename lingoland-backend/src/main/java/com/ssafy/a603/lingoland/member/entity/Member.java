package com.ssafy.a603.lingoland.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTaleMember;
import com.ssafy.a603.lingoland.group.entity.GroupMember;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
	name = "MEMBER_SEQ_GENERATOR",
	sequenceName = "member_id_seq",
	allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private int id;

	@Column(unique = true, nullable = false, length = 20)
	private String loginId;

	@Column(nullable = false, length = 20)
	private String nickname;

	@Column(nullable = false)
	private String password;

	@Lob
	private String profileImage;

	@Column(nullable = false)
	private long experiencePoint;

	@Column(nullable = false)
	private String rank;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private LocalDateTime deletedAt;

	@Column(nullable = false)
	private boolean isDeleted = false;

	@Column(nullable = false)
	private long runningPlayedCount;

	@Column(nullable = false)
	private long writingPlayedCount;

	private String refreshToken;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member")
	private List<GroupMember> groupMembers = new ArrayList<>();

	@OneToMany(mappedBy = "member")
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
}
