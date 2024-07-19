package com.ssafy.a603.lingoland.group.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.a603.lingoland.group.dto.UpdateGroupDTO;
import com.ssafy.a603.lingoland.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "\"group\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
	@SequenceGenerator(name = "group_seq", sequenceName = "group_id_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@Column(name = "password", nullable = false)
	private Integer password;

	@Column(name = "description", length = 200)
	private String description;

	@Column(name = "member_count", nullable = false, columnDefinition = "integer default 1")
	private int memberCount = 1;

	@Column(name = "group_image", length = 256)
	private String groupImage;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
	private boolean isDeleted = false;

	// 그룹장 Member 에 대한 정보가 없는데 어떤 방식을 생각하고 있는지 궁금합니다.
	@ManyToOne
	@JoinColumn(name = "leader_id")
	private Member leader;

	@OneToMany(mappedBy = "group")
	private List<GroupMember> groupMembers = new ArrayList<>();

	@Builder
	protected Group(String name, Integer password, String description, String groupImage, Member leader) {
		this.name = name;
		this.password = password;
		this.description = description;
		this.groupImage = groupImage;
		this.leader = leader;
		this.memberCount = 1;
		this.createdAt = LocalDateTime.now();
		this.isDeleted = false;
	}

	public void updateUser(UpdateGroupDTO request) {
		this.name = request.name();
		this.password = request.password();
		this.description = request.description();

	}

	public void delete() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}

	public void setGroupImagePath(String path) {
		this.groupImage = path;
	}

	//    id serial NOT NULL,
	//    name character varying(20) NOT NULL,
	//    password integer NOT NULL,
	//    description character varying(200) DEFAULT NULL,
	//    member_count integer NOT NULL DEFAULT 0,
	//    group_image character varying(256) DEFAULT NULL,
	//    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	//    deleted_at timestamp without time zone DEFAULT NULL,
	//    is_deleted boolean NOT NULL DEFAULT FALSE,
	//    PRIMARY KEY (id)
}
