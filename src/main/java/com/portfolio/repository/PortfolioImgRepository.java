package com.portfolio.repository;

import com.portfolio.entity.PortfolioImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioImgRepository extends JpaRepository<PortfolioImg, Long> {
    PortfolioImg findByPortfolioIdOrderByIdAsc(Long portfolioId);
}
