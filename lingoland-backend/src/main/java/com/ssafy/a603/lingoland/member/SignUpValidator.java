package com.ssafy.a603.lingoland.member;

import com.ssafy.a603.lingoland.member.dto.SignUpRequest;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;
        if(memberRepository.existsByLoginId(request.getLoginId())) {
            errors.rejectValue("loginId", "이미 사용중인 아이디입니다.");
        }
    }
}
