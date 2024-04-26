package com.portfolio.dto;

import com.portfolio.constant.PortfolioStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PortfolioDto {
    private Long id;

    private String title;

    private String writer;

    private String phoneNumber;

    private String email;

    private String oneLine;

    private String techStack;

    private String projectDetail;

    private PortfolioStatus portfolioStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
