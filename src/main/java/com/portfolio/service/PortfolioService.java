package com.portfolio.service;

import com.portfolio.dto.PortfolioFormDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    private final PortfolioImgService portfolioImgService;

    private final PortfolioImgRepository portfolioImgRepository;

    public Long saveItem(PortfolioFormDto portfolioFormDto, List<MultipartFile> portfolioImgFileList) throws Exception {

        //상품 등록
        Portfolio portfolio = portfolioFormDto.createPortfolio();
        portfolioRepository.save(portfolio);

        //이미지 등록
        PortfolioImg portfolioImg = new PortfolioImg();
        portfolioImg.setPortfolio(portfolio);

        portfolioImgService.saveItemImg(portfolioImg, portfolioImgFileList.get(i));

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = portfolioImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = portfolioRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = portfolioRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            portfolioImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return portfolioRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return portfolioRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
