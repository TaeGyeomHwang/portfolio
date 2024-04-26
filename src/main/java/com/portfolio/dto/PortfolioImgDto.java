package com.portfolio.dto;

import com.portfolio.entity.PortfolioImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PortfolioImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static PortfolioImgDto of(PortfolioImg portfolioImg) {
        return modelMapper.map(portfolioImg, PortfolioImgDto.class);
    }
}
