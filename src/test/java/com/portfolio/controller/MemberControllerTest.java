package com.portfolio.controller;

import com.portfolio.dto.MemberFormDto;
import com.portfolio.entity.Member;
import com.portfolio.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String nickName, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setNickName(nickName);
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String nickName = "test";
        String password = "1234";
        this.createMember(nickName, password);
        mockMvc.perform(formLogin().userParameter("nickName")
                        .loginProcessingUrl("/members/login")
                        .user(nickName).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String nickName = "test";
        String password = "1234";
        this.createMember(nickName, password);
        mockMvc.perform(formLogin().userParameter("nickName")
                        .loginProcessingUrl("/members/login")
                        .user(nickName).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}