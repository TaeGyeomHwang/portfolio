package com.portfolio.dto;

import com.portfolio.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;

    private String nickName;

    private String password;

    private Role role;
}
