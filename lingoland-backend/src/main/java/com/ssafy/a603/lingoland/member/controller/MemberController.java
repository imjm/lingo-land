package com.ssafy.a603.lingoland.member.controller;

import com.ssafy.a603.lingoland.member.dto.GetMemberInfoDto;
import com.ssafy.a603.lingoland.member.dto.UpdateNicknameDto;
import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.member.service.MemberService;
import com.ssafy.a603.lingoland.member.service.MemberServiceImpl;
import com.ssafy.a603.lingoland.member.validator.SignUpValidator;
import com.ssafy.a603.lingoland.member.dto.SignUpDto;
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
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpRequest, BindingResult bindingResult) {
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

    @GetMapping("/check/{loginId}")
    public ResponseEntity<?> checkIdDuplication(@PathVariable(value = "loginId") String loginId){
        if(memberService.checkIdDuplication(loginId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getMemberInfo(@CurrentUser CustomUserDetails customUserDetails) {
        GetMemberInfoDto getMemberInfoDto = memberService.getMemberInfo(customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body(getMemberInfoDto);
    }

    @PutMapping
    public ResponseEntity<?> updateNickname(@Valid @RequestBody UpdateNicknameDto updateNicknameDto,
                                            @CurrentUser CustomUserDetails customUserDetails) {
        memberService.updateNickname(updateNicknameDto, customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
