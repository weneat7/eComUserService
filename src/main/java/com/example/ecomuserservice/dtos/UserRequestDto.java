package com.example.ecomuserservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String name;
    private String userName;
    private String email;
    private String password;
}
