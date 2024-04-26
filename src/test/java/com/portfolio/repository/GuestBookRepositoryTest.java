package com.portfolio.repository;

import com.portfolio.constant.PortfolioStatus;
import com.portfolio.constant.Role;
import com.portfolio.entity.Guestbook;
import com.portfolio.entity.Member;
import com.portfolio.entity.Portfolio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class GuestBookRepositoryTest {

    @Autowired
    GuestBookRepository guestBookRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        Member member = new Member();
        member.setNickName("test");
        member.setPassword("1234");
        member.setRole(Role.ADMIN);

        return member;
    }

    public void createGuestbookList(){
        Member member = createMember();
        memberRepository.save(member);

        for (int i=1; i<=10; i++){
            Guestbook guestbook = new Guestbook();

            guestbook.setMember(member);
            guestbook.setComment("방명록내용"+i);
            guestbook.setRegTime(LocalDateTime.now());
            guestbook.setUpdateTime(LocalDateTime.now());

            guestBookRepository.save(guestbook);
        }
    }

    @Test
    @DisplayName("방명록 생성 테스트")
    public void guestbookTest() {
        this.createGuestbookList();
        List<Guestbook> guestbookList = guestBookRepository.findAll();
        for(Guestbook guestbook: guestbookList){
            System.out.println(guestbook.getMember().getNickName()+" "+guestbook.getComment()+" "+guestbook.getId());
        }
    }
}