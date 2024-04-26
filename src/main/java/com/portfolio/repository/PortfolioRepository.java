package com.portfolio.repository;

import com.portfolio.entity.Member;
import com.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long>, QuerydslPredicateExecutor<Portfolio> {

    List<Portfolio> findByTitle(String title);

    List<Portfolio> findByMember(Member member);

    @Query("select p from Portfolio p where p.projectDetail like %:projectDetail% ")
    List<Portfolio> findByProjectDetail(@Param("projectDetail") String projectDetail);
}
