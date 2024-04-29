package com.portfolio.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PortfolioMngDto {

    private Long id;

    private String title;

    private String imgUrl;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    @QueryProjection
    public PortfolioMngDto(Long id, String title, String imgUrl, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}
