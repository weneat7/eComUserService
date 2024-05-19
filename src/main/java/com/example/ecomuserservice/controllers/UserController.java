package com.example.ecomuserservice.controllers;

import com.example.ecomuserservice.dtos.LoginRequestDto;
import com.example.ecomuserservice.dtos.TokenResponseDto;
import com.example.ecomuserservice.dtos.UserRequestDto;
import com.example.ecomuserservice.dtos.UserResponseDto;
import com.example.ecomuserservice.models.Token;
import com.example.ecomuserservice.models.User;
import com.example.ecomuserservice.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(@Qualifier("SelfUserService") UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();

        Token token = userService.userLogIn(userName,password);
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setTokenValue(token.getValue());
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
        String name = userRequestDto.getName();
        String userName = userRequestDto.getUserName();
        String password = userRequestDto.getPassword();
        String email = userRequestDto.getEmail();

        User user = userService.userSignUp(name, userName, email, password);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody TokenResponseDto tokenResponseDto){
        String token = tokenResponseDto.getTokenValue();
        boolean result = userService.userLogOut(token);

        if(result)
            return new ResponseEntity<>(null,HttpStatus.OK);

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}
