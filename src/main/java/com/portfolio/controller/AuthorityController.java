//package com.portfolio.controller;
//
//import com.portfolio.entity.Guestbook;
//import com.portfolio.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Optional;
//
//@Controller
//@RequiredArgsConstructor
//public class AuthorityController {
//
//    private final MemberService memberService;
//
//    @GetMapping(value = {"/admin/authority"})
//    public String authorityList(Optional<Integer> page, Model model) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 10);
//        Page<Guestbook> guestbooks = memberService.getGuestbookMngPage(pageable);
//        model.addAttribute("guestbooks", guestbooks);
//        model.addAttribute("maxPage", 5);
//
//        return "guestbook/guestbookMng";
//    }
//}
