package com.ssafy.a603.lingoland.member.controller;

import com.ssafy.a603.lingoland.member.dto.MemberInfoDto;
import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.member.service.MemberService;
import com.ssafy.a603.lingoland.member.validator.SignUpValidator;
import com.ssafy.a603.lingoland.member.dto.SignUpRequest;
import com.ssafy.a603.lingoland.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;
    private final SignUpValidator signUpValidator;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        signUpValidator.validate(signUpRequest, bindingResult);
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getCode)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        Member member = memberService.saveNewMember(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping
    public ResponseEntity<?> getMember(@CurrentUser CustomUserDetails customUserDetails) {
        MemberInfoDto memberInfoDto = memberService.getMemberInfo(customUserDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberInfoDto);
    }
}
