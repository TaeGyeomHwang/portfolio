package com.portfolio.repository;

import com.portfolio.dto.PortfolioMngDto;
import com.portfolio.dto.PortfolioSearchDto;
import com.portfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PortfolioRepositoryCustom {
    Page<Portfolio> getAdminPortfolioPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable);

    Page<PortfolioMngDto> getPortfolioMngPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable);
}
