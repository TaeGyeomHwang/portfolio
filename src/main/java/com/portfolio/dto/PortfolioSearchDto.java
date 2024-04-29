package com.portfolio.dto;

import com.portfolio.constant.PortfolioStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioSearchDto {
    private String searchDateType;
    private PortfolioStatus searchStatus;
    private String searchBy;
    private String  searchQuery = "";
}
