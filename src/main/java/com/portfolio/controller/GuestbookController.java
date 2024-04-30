package com.portfolio.controller;

import com.portfolio.dto.GuestbookFormDto;
import com.portfolio.entity.Guestbook;
import com.portfolio.service.GuestbookService;
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

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;


    @GetMapping(value = "/guestbook/new")
    public String guestbookForm(Model model) {
        model.addAttribute("guestbookFormDto", new GuestbookFormDto());
        return "guestbook/guestbookForm";
    }

    @PostMapping(value = "/guestbook/new")
    public String newGuestbook(@Valid GuestbookFormDto guestbookFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "guestbook/guestbookForm";
        }

        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String nickName = authentication.getName();
            guestbookService.saveGuestbook(nickName, guestbookFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "방명록 등록 중 에러가 발생하였습니다.");
            return "guestbook/guestbookForm";
        }

        return "redirect:/guestbook";
    }

    @GetMapping(value = {"/guestbook", "/guestbook/{page}"})
    public String guestbookList(Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<Guestbook> guestbooks = guestbookService.getGuestbookMngPage(pageable);
        model.addAttribute("guestbooks", guestbooks);
        model.addAttribute("maxPage", 5);

        return "guestbook/guestbookMng";
    }

    @GetMapping(value = "/guestbook/detail/{guestbookId}")
    public String guestbookDtl(Model model, @PathVariable("guestbookId") Long guestbookId) {
        Guestbook guestbook = guestbookService.getGuestbookDtl(guestbookId);
        model.addAttribute("guestbook", guestbook);
        return "guestbook/guestbookDtl";
    }

    @PostMapping(value = "/guestbook/detail/{guestbookId}")
    public @ResponseBody ResponseEntity guestbookUpdate(@PathVariable("guestbookId") Long guestbookId,  @RequestParam("title") String title,
                                                        @RequestParam("content") String content, Principal principal) {
        if (!guestbookService.validateId(guestbookId, principal.getName())) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String nickName = authentication.getName();

        guestbookService.updateGuestbook(nickName, guestbookId, title, content);

        return new ResponseEntity<Long>(guestbookId, HttpStatus.OK);
    }

    @PostMapping(value = "/guestbook/detail/delete/{guestbookId}")
    public @ResponseBody ResponseEntity guestbookDelete(@PathVariable("guestbookId") Long guestbookId, Principal principal) {
        if (!guestbookService.validateId(guestbookId, principal.getName())) {
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        guestbookService.deleteGuestbook(guestbookId);
        return new ResponseEntity<Long>(guestbookId, HttpStatus.OK);
    }
}
