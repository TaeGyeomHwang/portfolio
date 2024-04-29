package com.portfolio.repository;

import com.portfolio.constant.PortfolioStatus;
import com.portfolio.dto.PortfolioMngDto;
import com.portfolio.dto.PortfolioSearchDto;
import com.portfolio.dto.QPortfolioMngDto;
import com.portfolio.entity.Portfolio;
import com.portfolio.entity.QPortfolio;
import com.portfolio.entity.QPortfolioImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PortfolioRepositoryCustomImpl implements PortfolioRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QPortfolio portfolio = QPortfolio.portfolio;

    private BooleanExpression searchStatusEq(PortfolioStatus searchStatus) {
        return searchStatus == null ? null : portfolio.portfolioStatus.eq(searchStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return portfolio.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("title", searchBy)) {
            return portfolio.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return portfolio.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Portfolio> getAdminPortfolioPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable) {

        List<Portfolio> content = queryFactory
                .selectFrom(portfolio)
                .where(regDtsAfter(portfolioSearchDto.getSearchDateType()),
                        searchStatusEq(portfolioSearchDto.getSearchStatus()),
                        searchByLike(portfolioSearchDto.getSearchBy(),
                                portfolioSearchDto.getSearchQuery()))
                .orderBy(portfolio.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(portfolio)
                .where(regDtsAfter(portfolioSearchDto.getSearchDateType()),
                        searchStatusEq(portfolioSearchDto.getSearchStatus()),
                        searchByLike(portfolioSearchDto.getSearchBy(), portfolioSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : portfolio.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<PortfolioMngDto> getPortfolioMngPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable) {
        QPortfolioImg portfolioImg = QPortfolioImg.portfolioImg;

        List<PortfolioMngDto>content = queryFactory
                .select(
                        new QPortfolioMngDto(
                                portfolio.id,
                                portfolio.title,
                                portfolioImg.imgUrl,
                                portfolio.regTime,
                                portfolio.updateTime
                        )
                )
                .from(portfolioImg)
                .join(portfolioImg.portfolio, portfolio)
                .where(searchByLike("title", portfolioSearchDto.getSearchQuery()))
                .orderBy(portfolio.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(Wildcard.count).from(portfolioImg)
                .join(portfolioImg.portfolio, portfolio)
                .where(searchByLike("title", portfolioSearchDto.getSearchQuery()))
                .fetchOne();

        if(total == null){
            total = 0L;
        }

        return new PageImpl<>(content, pageable, total);    }
}
