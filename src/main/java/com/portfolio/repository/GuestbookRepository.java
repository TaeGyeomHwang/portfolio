package com.portfolio.repository;

import com.portfolio.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    @Query("SELECT g from Guestbook g")
    Page<Guestbook> getGuestbookMngPage(Pageable pageable);

    Page<Guestbook> findAllByOrderByRegTimeDesc(Pageable pageable);

}
