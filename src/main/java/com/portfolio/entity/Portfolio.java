package com.portfolio.entity;

import com.portfolio.constant.PortfolioStatus;
import com.portfolio.dto.PortfolioFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio")
@Getter
@Setter
@ToString
public class Portfolio extends BaseEntity{

    @Id
    @Column(name = "portfolio_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 50, name = "portfolio_title")
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Lob
    @Column(nullable = false)
    private String oneLine;

    @Lob
    @Column(nullable = false)
    private String techStack;

    @Lob
    @Column(nullable = false)
    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private PortfolioStatus portfolioStatus;

    public Portfolio() {
        this.portfolioStatus = PortfolioStatus.GENERAL; // 기본 상태를 GENERAL로 설정
    }

    public void updatePortfolio(PortfolioFormDto portfolioFormDto){
        this.title = portfolioFormDto.getTitle();
        this.writer = portfolioFormDto.getWriter();
        this.phoneNumber = portfolioFormDto.getPhoneNumber();
        this.email = portfolioFormDto.getEmail();
        this.oneLine = portfolioFormDto.getOneLine();
        this.techStack = portfolioFormDto.getTechStack();
        this.projectDetail = portfolioFormDto.getProjectDetail();
        this.portfolioStatus = portfolioFormDto.getPortfolioStatus();
    }

}
