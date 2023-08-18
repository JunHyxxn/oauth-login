package com.junhyxxn.back.user.service;

import com.junhyxxn.back.auth.service.PrincipalDetails;
import com.junhyxxn.back.auth.service.TokenService;
import com.junhyxxn.back.domain.entity.User;
import com.junhyxxn.back.user.controller.dto.SignUpDto;
import com.junhyxxn.back.user.mapper.UserMapper;
import com.junhyxxn.back.user.repository.UserRepository;
import com.junhyxxn.back.user.repository.query.UserQueryRepository;
import com.junhyxxn.back.user.service.dto.JwtResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 메서드
     *
     * @param signUpDto : 회원 가입 폼
     * @return JwtResponseDto : 토큰 정보와 추가 입력 정보 필요 여부 데이터 DTO
     */
    @Override
    public JwtResponseDto signUp(SignUpDto signUpDto) throws IllegalArgumentException {
        // 1. Email & Provider Validation Check
        userQueryRepository.findByEmailAndProvider(signUpDto.getEmail(), signUpDto.getProvider())
                           .ifPresent(
                                   u -> {
                                       throw new IllegalArgumentException("이미 회원 가입된 사용자입니다.");
                                   }
                           );

        // 2. 회원 정보 저장
        User user = joinProcess(signUpDto);

        // 3. 토큰 생성 및 저장
        return tokenService.generateToken(
                PrincipalDetails.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .provider(user.getProvider())
                                .password(user.getPassword())
                                .isNew(false)
                                .authorities(
                                        List.of(new SimpleGrantedAuthority(user.getRole().toString()))
                                )
                                .build()
        );
    }

    private User joinProcess(SignUpDto signUpDto) {
        signUpDto.encodingPassword(passwordEncoder);
        return userRepository.save(
                UserMapper.toUserEntity(signUpDto)
        );
    }

}
