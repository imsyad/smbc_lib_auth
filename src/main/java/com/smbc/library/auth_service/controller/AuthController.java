package com.smbc.library.auth_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smbc.library.auth_service.dto.ResponseDto;
import com.smbc.library.auth_service.dto.UserLoginDto;
import com.smbc.library.auth_service.dto.UserRegisterDto;
import com.smbc.library.auth_service.service.iservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseDto<?> register(@RequestBody @Valid UserRegisterDto userData) {
        return userService.register(userData);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody @Valid UserLoginDto userLogin) {
        return userService.login(userLogin);
    }   
}
