package com.portfolio.controller;

import com.portfolio.dto.GuestbookFormDto;
import com.portfolio.service.GuestbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String newGuestbook(@Valid GuestbookFormDto guestbookFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "guestbook/guestbookForm";
        }

        try {
            guestbookService.saveGuestbook(guestbookFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "방명록 등록 중 에러가 발생하였습니다.");
            return "guestbook/guestbookForm";
        }

        return "redirect:/";
    }

//    @PostMapping("/post")
//    public ResponseEntity<GuestbookDto> postComment(@RequestBody GuestbookDto guestbookDto,
//                                                    @AuthenticationPrincipal UserDetails userDetails) {
//        GuestbookDto savedGuestbookDto = guestbookService.postComment(guestbookDto, userDetails.getUsername());
//        return ResponseEntity.ok(savedGuestbookDto);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<GuestbookDto> updateComment(@PathVariable Long id, @RequestBody GuestbookDto guestbookDto) {
//        try {
//            GuestbookDto updatedGuestbookDto = guestbookService.updateComment(id, guestbookDto);
//            return ResponseEntity.ok(updatedGuestbookDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
//        try {
//            guestbookService.deleteComment(id);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
