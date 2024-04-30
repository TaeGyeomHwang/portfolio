package com.portfolio.repository;

import com.portfolio.dto.MemberDto;
import com.portfolio.dto.PortfolioMngDto;
import com.portfolio.dto.PortfolioSearchDto;
import com.portfolio.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickName(String nickName);

    @Query("SELECT m from Member m")
    Page<MemberDto> getMemberMngPage(Pageable pageable);
}
