package com.ssafy.a603.lingoland.problem.entity;

import com.ssafy.a603.lingoland.global.entity.BaseTimeEntity;
import com.ssafy.a603.lingoland.member.entity.Member;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemMember extends BaseTimeEntity {

    @EmbeddedId
    private ProblemMemberId id;

    private int submittedAnswer;

    @Builder
    public ProblemMember(int submittedAnswer, ProblemMemberId id) {
        this.submittedAnswer = submittedAnswer;
        this.id = id;
    }

}
