package com.junhyxxn.back.user.service;

import com.junhyxxn.back.user.controller.dto.SignUpDto;
import com.junhyxxn.back.user.service.dto.JwtResponseDto;

public interface UserService {

    JwtResponseDto signUp(SignUpDto signUpDto) throws IllegalArgumentException;

}
