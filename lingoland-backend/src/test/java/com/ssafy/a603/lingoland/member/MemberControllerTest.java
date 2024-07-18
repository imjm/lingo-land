package com.ssafy.a603.lingoland.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.member.dto.SignUpRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;


    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signUp_with_wrong_input() throws Exception {
        SignUpRequest signUpRequest = createSignUpRequest("", "123456", "test123");

        performSignUp(signUpRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").exists()); // Assuming validation error is returned as a list of errors
    }

    @Transactional
    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signUp_with_correct_input() throws Exception {
        SignUpRequest signUpRequest = createSignUpRequest("test1", "123456", "test1234");

        performSignUp(signUpRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nickname").value("test1"))
                .andExpect(jsonPath("$.loginId").value("test1234"));

        Member member = memberRepository.findByLoginId("test1234");
        assertNotNull(member);
        assertNotEquals(member.getPassword(), "123456"); // Check that the password is hashed
    }

    private SignUpRequest createSignUpRequest(String nickname, String password, String loginId) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setNickname(nickname);
        signUpRequest.setPassword(password);
        signUpRequest.setLoginId(loginId);
        return signUpRequest;
    }

    private ResultActions performSignUp(SignUpRequest signUpRequest) throws Exception {
        return mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)));
    }
}