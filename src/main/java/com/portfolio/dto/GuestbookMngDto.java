package com.portfolio.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookMngDto {
    private Long id;

    private String title;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    @QueryProjection
    public GuestbookMngDto(Long id, String title, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }
}
