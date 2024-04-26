package com.portfolio.repository;

import com.portfolio.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickName(String nickName);
}
