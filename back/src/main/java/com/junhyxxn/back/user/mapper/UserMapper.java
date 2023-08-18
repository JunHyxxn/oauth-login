package com.junhyxxn.back.user.mapper;

import com.junhyxxn.back.domain.entity.User;
import com.junhyxxn.back.user.controller.dto.SignUpDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User toUserEntity(SignUpDto signUpDto) {
        return User.builder()
                   .email(signUpDto.getEmail())
                   .provider(signUpDto.getProvider())
                   .password(signUpDto.getPassword())
                   .nickname(signUpDto.getNickname())
                   .gender(signUpDto.getGender())
                   .age(signUpDto.getAge())
                   .build();
    }

}
