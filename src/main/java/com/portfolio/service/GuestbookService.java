package com.portfolio.service;

import com.portfolio.dto.GuestbookFormDto;
import com.portfolio.entity.Guestbook;
import com.portfolio.repository.GuestbookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public Guestbook saveGuestbook(GuestbookFormDto guestbookFormDto) throws Exception{
        Guestbook guestbook = Guestbook.createGuestbook(guestbookFormDto);

        return guestbookRepository.save(guestbook);
    }
}
