package com.junhyxxn.back.auth.service;

import com.junhyxxn.back.common.util.JwtUtil;
import com.junhyxxn.back.domain.type.RedisKey;
import com.junhyxxn.back.user.service.dto.JwtResponseDto;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    /**
     * 토큰 생성 및 저장 메서드
     *
     * @param principalDetails : 인증에 성공한 주체
     * @return JwtResponseDto : 토큰 정보와 추가 정보 입력 필요 여부를 담은 데이터 DTO
     */
    @Override
    public JwtResponseDto generateToken(PrincipalDetails principalDetails) {
        // Generate Tokens
        String accessToken = jwtUtil.generateAccessToken(principalDetails);
        String refreshToken = jwtUtil.generateRefreshToken(principalDetails);

        // Redis에 RTK 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(RedisKey.REFRESH_TOKEN.getKey(principalDetails.getName()), refreshToken, Duration.ofDays(21));

        return JwtResponseDto.builder()
                             .accessToken(accessToken)
                             .refreshToken(refreshToken)
                             .isNew(principalDetails.getIsNew())
                             .build();
    }

}
