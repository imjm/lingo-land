package com.ssafy.a603.lingoland.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(
        name="MEMBER_SEQ_GENERATOR",
        sequenceName = "member_id_seq",
        allocationSize = 1
)
public class Member {

    @Id @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private int id;

    @Column(unique = true, nullable = false, length = 20)
    private String loginId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String profile_image;

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
}
