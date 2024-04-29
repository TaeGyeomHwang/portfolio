package com.portfolio.dto;

import com.portfolio.entity.Guestbook;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class GuestbookFormDto {

    private Long id;

    private String title;

    private String content;

    private static ModelMapper modelMapper = new ModelMapper();

    public Guestbook createGuestbook(){return modelMapper.map(this, Guestbook.class);}

}
