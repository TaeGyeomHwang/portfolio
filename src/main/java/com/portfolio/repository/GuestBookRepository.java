package com.portfolio.repository;

import com.portfolio.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<Guestbook, Long> {
}
