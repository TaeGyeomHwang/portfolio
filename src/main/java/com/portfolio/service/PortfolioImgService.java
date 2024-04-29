package com.portfolio.service;

import com.portfolio.entity.PortfolioImg;
import com.portfolio.repository.PortfolioImgRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioImgService {
    @Value("${portfolioImgLocation}")
    private String portfolioImgLocation;
    
    private final PortfolioImgRepository portfolioImgRepository;

    private final FileService fileService;

    public void savePortfolioImg(PortfolioImg portfolioImg, MultipartFile portfolioImgFile) throws Exception {
        String oriImgName = portfolioImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(portfolioImgLocation, oriImgName, portfolioImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        portfolioImg.updatePortfolioImg(oriImgName, imgName, imgUrl);
        portfolioImgRepository.save(portfolioImg);
    }

    public void updatePortfolioImg(Long portfolioImg, MultipartFile portFolioImgFile) throws Exception{
        if(!portFolioImgFile.isEmpty()){
            System.out.println("portfolioImg의 id: "+portfolioImg);
            PortfolioImg portfolioItemImg = portfolioImgRepository.findById(portfolioImg)
                    .orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(portfolioItemImg.getImgName())){
                fileService.deleteFile(portfolioImgLocation+"/"+portfolioItemImg.getImgName());
            }

            String oriImgName = portFolioImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(portfolioImgLocation, oriImgName, portFolioImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            portfolioItemImg.updatePortfolioImg(oriImgName, imgName, imgUrl);
        }else {
            System.out.println("portFolioImgFile이 null입니다.");
        }
    }
}
