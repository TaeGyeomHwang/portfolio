package com.portfolio.entity;

import com.portfolio.dto.MemberFormDto;
import com.portfolio.repository.GuestbookRepository;
import com.portfolio.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class GuestbookTest {
    @Autowired
    GuestbookRepository guestbookRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setNickName("test");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto,passwordEncoder);
    }
    
    @Test
    @DisplayName("방명록 생성 테스트")
    public void createGuestbookTest(){
        Member member = createMember();
        memberRepository.save(member);

        Guestbook guestbook = new Guestbook();
        guestbook.setMember(member);
        guestbook.setTitle("title");
        guestbook.setContent("content");
        guestbookRepository.save(guestbook);
    }
}