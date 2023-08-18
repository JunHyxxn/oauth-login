package com.junhyxxn.back.auth.service;

import com.junhyxxn.back.user.service.dto.JwtResponseDto;

public interface TokenService {

    JwtResponseDto generateToken(PrincipalDetails principalDetails);

}
