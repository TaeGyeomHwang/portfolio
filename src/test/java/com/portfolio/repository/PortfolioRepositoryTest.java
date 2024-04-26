package com.portfolio.repository;

import com.portfolio.entity.Portfolio;
import com.portfolio.constant.PortfolioStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Test
    @DisplayName("포트폴리오 저장 테스트")
    public void createPortfolioTest(){
        Portfolio portfolio = new Portfolio();
        portfolio.setTitle("테스트 포트폴리오");
        portfolio.setWriter("테스트 계정");
        portfolio.setPhoneNumber("010-0000-0000");
        portfolio.setEmail("test@test.test");
        portfolio.setOneLine("테스트용 포트폴리오입니다.");
        portfolio.setTechStack("SpringBoot3, SpringSecurity6, SpringData(JPA), Mysql, Thymeleaf, Html/Css/JS");
        portfolio.setProjectDetail("개인 포트폴리오 페이지를 구축한 프로젝트입니다.");
        portfolio.setPortfolioStatus(PortfolioStatus.REPRESENTATIVE);
        portfolio.setRegTime(LocalDateTime.now());
        portfolio.setUpdateTime(LocalDateTime.now());

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        System.out.println(savedPortfolio);
    }

    public void createPortfolioList(){
        for (int i=1; i<=10; i++){
            Portfolio portfolio = new Portfolio();
            portfolio.setTitle("테스트 포트폴리오"+i);
            portfolio.setWriter("테스트 계정");
            portfolio.setPhoneNumber("010-0000-0000");
            portfolio.setEmail("test@test.test");
            portfolio.setOneLine("테스트용 포트폴리오입니다.");
            portfolio.setTechStack("SpringBoot3, SpringSecurity6, SpringData(JPA), Mysql, Thymeleaf, Html/Css/JS");
            portfolio.setProjectDetail("개인 포트폴리오 페이지를 구축한 프로젝트입니다.");
            portfolio.setPortfolioStatus(PortfolioStatus.REPRESENTATIVE);
            portfolio.setRegTime(LocalDateTime.now());
            portfolio.setUpdateTime(LocalDateTime.now());

            Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        }
    }

    @Test
    @DisplayName("포트폴리오 제목 조회 테스트")
    public void findByTitleTest(){
        this.createPortfolioList();
        List<Portfolio> portfolioList = portfolioRepository.findByTitle("테스트 포트폴리오1");
        for(Portfolio portfolio: portfolioList){
            System.out.println(portfolio.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 포트폴리오 조회 테스트")
    public void findByProjectDetailTest() {
        this.createPortfolioList();
        List<Portfolio> portfolioList = portfolioRepository.findByProjectDetail("개인");
        for (Portfolio portfolio : portfolioList) {
            System.out.println(portfolio.toString());
        }
    }
}