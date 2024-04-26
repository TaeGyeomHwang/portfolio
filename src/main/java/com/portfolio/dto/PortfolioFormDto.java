package com.portfolio.dto;

import com.portfolio.constant.PortfolioStatus;
import com.portfolio.entity.Portfolio;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PortfolioFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String writer;

    @NotBlank(message = "연락처는 필수 입력 값입니다.")
    private String phoneNumber;

    @NotBlank(message = "메일 주소는 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "한줄 소개는 필수 입력 값입니다.")
    private String oneLine;
    
    @NotBlank(message = "나의 기술 스택은 필수 입력 값입니다.")
    private String techStack;

    @NotBlank(message = "나의 프로젝트 소개는 필수 입력 값입니다.")
    private String projectDetail;

    private PortfolioStatus portfolioStatus;

    private PortfolioImgDto portfolioImgDto;

    private Long portfolioImgId;

    private static ModelMapper modelMapper = new ModelMapper();

    public Portfolio createPortfolio(){
        return modelMapper.map(this, Portfolio.class);
    }

    public static PortfolioFormDto of(Portfolio portfolio){
        return modelMapper.map(portfolio, PortfolioFormDto.class);
    }
}
