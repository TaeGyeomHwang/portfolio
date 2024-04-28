package com.portfolio.service;

import com.portfolio.dto.PortfolioFormDto;
import com.portfolio.entity.Portfolio;
import com.portfolio.entity.PortfolioImg;
import com.portfolio.repository.PortfolioImgRepository;
import com.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
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

//    @Transactional(readOnly = true)
//    public PortfolioFormDto getPortfolioDtl(Long itemId) {
//        PortfolioImg portfolioImg = portfolioImgRepository.findByItemIdOrderByIdAsc(itemId);
//        PortfolioImgDto portfolioImgDto = new PortfolioImgDto();
//        for (ItemImg itemImg : itemImgList) {
//            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//            itemImgDtoList.add(itemImgDto);
//        }
//
//        Item item = portfolioRepository.findById(itemId)
//                .orElseThrow(EntityNotFoundException::new);
//        ItemFormDto itemFormDto = ItemFormDto.of(item);
//        itemFormDto.setItemImgDtoList(itemImgDtoList);
//        return itemFormDto;
//    }
//
//    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
//        //상품 수정
//        Item item = portfolioRepository.findById(itemFormDto.getId())
//                .orElseThrow(EntityNotFoundException::new);
//        item.updateItem(itemFormDto);
//        List<Long> itemImgIds = itemFormDto.getItemImgIds();
//
//        //이미지 등록
//        for (int i = 0; i < itemImgFileList.size(); i++) {
//            portfolioImgService.updateItemImg(itemImgIds.get(i),
//                    itemImgFileList.get(i));
//        }
//
//        return item.getId();
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        return portfolioRepository.getAdminItemPage(itemSearchDto, pageable);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        return portfolioRepository.getMainItemPage(itemSearchDto, pageable);
//    }
}
