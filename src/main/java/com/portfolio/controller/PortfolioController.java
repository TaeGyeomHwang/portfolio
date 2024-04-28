package com.portfolio.controller;

import com.portfolio.dto.PortfolioFormDto;
import com.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping(value = "/admin/portfolio/new")
    public String itemForm(Model model) {
        model.addAttribute("portfolioFormDto", new PortfolioFormDto());
        return "portfolio/portfolioForm";
    }

    
}
