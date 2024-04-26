package com.portfolio.controller;

import com.portfolio.dto.PortfolioFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

    @GetMapping(value = "/admin/portfolio/new")
    public String itemForm(Model model) {
        model.addAttribute("portfolioFormDto", new PortfolioFormDto());
        return "portfolio/portfolioForm";
    }
}
