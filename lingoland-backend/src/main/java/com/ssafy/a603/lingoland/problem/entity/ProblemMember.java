package com.ssafy.a603.lingoland.problem.entity;

import com.ssafy.a603.lingoland.global.entity.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemMember extends BaseTimeEntity {

    @EmbeddedId
    private ProblemMemberId id;

    private int submittedAnswer;

    private boolean isCorrect;

    @Builder
    public ProblemMember(int submittedAnswer, ProblemMemberId id) {
        this.submittedAnswer = submittedAnswer;
        this.id = id;
    }

    public void updateIsCorrect() {
        if(this.isCorrect == false) {
            this.isCorrect = true;
        }
    }
}
