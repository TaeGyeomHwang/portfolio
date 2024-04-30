package com.portfolio.controller;

import com.portfolio.dto.PortfolioFormDto;
import com.portfolio.dto.PortfolioMngDto;
import com.portfolio.dto.PortfolioSearchDto;
import com.portfolio.service.PortfolioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping(value = "/admin/portfolio/new")
    public String itemForm(Model model) {
        model.addAttribute("portfolioFormDto", new PortfolioFormDto());
        return "portfolio/portfolioForm";
    }

    @PostMapping(value = "/admin/portfolio/new")
    public String portfolioNew(@Valid PortfolioFormDto portfolioFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("portfolioImgFile") MultipartFile portfolioImgFile) {

        if (bindingResult.hasErrors()) {
            return "portfolio/portfolioForm";
        }

        if (portfolioImgFile == null && portfolioFormDto.getId() == null) {
            model.addAttribute("errorMessage", "프로필 이미지는 필수 입력 값 입니다.");
            return "portfolio/portfolioForm";
        }

        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String nickName = authentication.getName();

            portfolioService.savePortfolio(nickName, portfolioFormDto, portfolioImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "포트폴리오 등록 중 에러가 발생하였습니다.");
            return "portfolio/portfolioForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/portfolio/{portfolioId}")
    public String portfolioDtl(@PathVariable("portfolioId") Long portfolioId, Model model) {
        try {
            PortfolioFormDto portfolioFormDto = portfolioService.getPortfolioDtl(portfolioId);
            model.addAttribute("portfolioFormDto", portfolioFormDto);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 포트폴리오 입니다.");
            model.addAttribute("portfolioFormDto", new PortfolioFormDto());
            return "portfolio/portfolioForm";
        }

        return "portfolio/portfolioForm";
    }

    @PostMapping(value = "/admin/portfolio/{portfolioId}")
    public String portfolioUpdate(@Valid PortfolioFormDto portfolioFormDto, BindingResult bindingResult,
                                  @RequestParam("portfolioImgFile") MultipartFile portfolioImgFile, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult 에러 발생");
            return "portfolio/portfolioForm";
        }

        if (portfolioImgFile == null && portfolioFormDto.getId() == null) {
            model.addAttribute("errorMessage", "프로필 이미지는 필수 입력 값 입니다.");
            return "portfolio/portfolioForm";
        }

        try {
            portfolioService.updatePortfolio(portfolioFormDto, portfolioImgFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", "포트폴리오 수정 중 에러가 발생하였습니다.");
            return "portfolio/portfolioForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/portfolios", "/admin/portfolios/{page}"})
    public String portfolioManage(PortfolioSearchDto portfolioSearchDto, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0),3);
        Page<PortfolioMngDto> portfolios = portfolioService.getPortfolioMngPage(portfolioSearchDto,pageable);
        model.addAttribute("portfolios",portfolios);
        model.addAttribute("portfolioSearchDto",portfolioSearchDto);
        model.addAttribute("maxPage",5);
        return "portfolio/portfolioMng";
    }

    @GetMapping(value = "/portfolio/{portfolioId}")
    public String portfolioDtl(Model model, @PathVariable("portfolioId") Long portfolioId){
        PortfolioFormDto portfolioFormDto = portfolioService.getPortfolioDtl(portfolioId);
        model.addAttribute("portfolio",portfolioFormDto);
        return "portfolio/portfolioDtl";
    }

    @PostMapping("/admin/portfolio/delete/{portfolioId}")
    public String portfolioDelete(@PathVariable Long portfolioId, Model model){
        try {
            portfolioService.deletePortfolio(portfolioId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "포트폴리오 삭제 중 에러가 발생하였습니다.");
            return "redirect:/";
        }

        return "redirect:/";
    }

}
