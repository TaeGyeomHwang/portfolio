package com.portfolio.service;

import com.portfolio.constant.PortfolioStatus;
import com.portfolio.dto.*;
import com.portfolio.entity.Portfolio;
import com.portfolio.entity.PortfolioImg;
import com.portfolio.repository.PortfolioImgRepository;
import com.portfolio.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    private final PortfolioImgService portfolioImgService;

    private final PortfolioImgRepository portfolioImgRepository;

    public Long savePortfolio(PortfolioFormDto portfolioFormDto, MultipartFile portfolioImgFile) throws Exception {

        // 포트폴리오 등록
        Portfolio portfolio = portfolioFormDto.createPortfolio();
        portfolioRepository.save(portfolio);

        //이미지 등록
        PortfolioImg portfolioImg = new PortfolioImg();
        portfolioImg.setPortfolio(portfolio);

        portfolioImgService.savePortfolioImg(portfolioImg, portfolioImgFile);

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public PortfolioFormDto getPortfolioDtl(Long portfolioId) {
        PortfolioImg portfolioImg = portfolioImgRepository.findByPortfolioIdOrderByIdAsc(portfolioId);
        PortfolioImgDto portfolioImgDto = PortfolioImgDto.of(portfolioImg);

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(EntityNotFoundException::new);
        PortfolioFormDto portfolioFormDto = PortfolioFormDto.of(portfolio);
        portfolioFormDto.setPortfolioImgDto(portfolioImgDto);

        return portfolioFormDto;
    }

    public Long updatePortfolio(PortfolioFormDto portfolioFormDto, MultipartFile multipartFile) throws Exception {
        //상품 수정
        Portfolio portfolio = portfolioRepository.findById(portfolioFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        portfolio.updatePortfolio(portfolioFormDto);
        Long portfolioImgId = portfolioFormDto.getPortfolioImgId();

        //이미지 등록
        portfolioImgService.updatePortfolioImg(portfolioImgId, multipartFile);

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public Page<Portfolio> getAdminItemPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable) {
        return portfolioRepository.getAdminPortfolioPage(portfolioSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PortfolioMngDto> getPortfolioMngPage(PortfolioSearchDto portfolioSearchDto, Pageable pageable) {
        return portfolioRepository.getPortfolioMngPage(portfolioSearchDto, pageable);
    }

    public Long deletePortfolio(Long portfolioId) {
        PortfolioImg portfolioImg = portfolioImgRepository.findByPortfolioIdOrderByIdAsc(portfolioId);
        if (portfolioImg != null) {
            portfolioImgRepository.delete(portfolioImg);
        }

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(EntityNotFoundException::new);
        portfolioRepository.delete(portfolio);

        return portfolio.getId();
    }

    public Portfolio findByPortfolioStatus() {
        Portfolio portfolio = portfolioRepository.findByPortfolioStatus(PortfolioStatus.REPRESENTATIVE);

        return portfolio;
    }

    @Transactional
    public void updateExistingPortfoliosToGeneral(Long newPortfolioId) {
        portfolioRepository.updatePortfoliosToGeneralExcept(newPortfolioId);
    }
}
