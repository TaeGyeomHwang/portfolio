package com.portfolio.service;

import com.portfolio.dto.GuestbookFormDto;
import com.portfolio.entity.Guestbook;
import com.portfolio.entity.Member;
import com.portfolio.repository.GuestbookRepository;
import com.portfolio.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestbookService {

    private final MemberRepository memberRepository;

    private final GuestbookRepository guestbookRepository;

    public Guestbook saveGuestbook(String nickName, GuestbookFormDto guestbookFormDto) throws Exception {

        Guestbook guestbook = guestbookFormDto.createGuestbook();
        Member member = memberRepository.findByNickName(nickName);
        guestbook.setMember(member);

        return guestbookRepository.save(guestbook);
    }

    @Transactional
    public Page<Guestbook> getGuestbookMngPage(Pageable pageable) {
        return guestbookRepository.findAllByOrderByRegTimeDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Guestbook getGuestbookDtl(Long guestbookId) {

        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(EntityNotFoundException::new);

        return guestbook;
    }

    public Long updateGuestbook(String nickName, Long guestbookId, String title, String content) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(EntityNotFoundException::new);
        guestbook.setTitle(title);
        guestbook.setContent(content);

        Member member = memberRepository.findByNickName(nickName);
        guestbook.setMember(member);

        guestbookRepository.save(guestbook);

        return guestbook.getId();
    }

    @Transactional(readOnly = true)
    public boolean validateId(Long guestbookId, String nickName) {
        Member curMember = memberRepository.findByNickName(nickName);
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = guestbook.getMember();

        if (!StringUtils.equals(curMember.getNickName(), savedMember.getNickName())) {
            return false;
        }

        return true;
    }

    public void deleteGuestbook(Long guestbookId) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(EntityNotFoundException::new);
        guestbookRepository.delete(guestbook);
    }
}
