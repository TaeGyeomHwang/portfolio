package com.portfolio.controller;

import com.portfolio.dto.PortfolioFormDto;
import com.portfolio.entity.Portfolio;
import com.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PortfolioService portfolioService;

    @GetMapping("/")
    public String index(Model model) {
        Portfolio portfolio = portfolioService.findByPortfolioStatus();

        if (portfolio != null) {
            PortfolioFormDto portfolioFormDto = portfolioService.getPortfolioDtl(portfolio.getId());
            model.addAttribute("portfolio",portfolioFormDto);
            portfolioService.updateExistingPortfoliosToGeneral(portfolio.getId());
            return "portfolio/portfolioDtl";
        } else {
            return "/main";
        }
    }
}
