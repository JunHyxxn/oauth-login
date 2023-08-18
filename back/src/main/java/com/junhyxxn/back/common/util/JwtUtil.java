package com.junhyxxn.back.common.util;

import com.junhyxxn.back.auth.service.PrincipalDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 21 * DAY;
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 30 * MINUTE;
    private static final String UID_CLAIM_KEY = "uid";
    private static final String AUTHORITIES_CLAIM_KEY = "auth";
    private final String tokenHeaderName;
    private final Key key;

    public JwtUtil(@Value("${jwt.secret-key}") String secret,
                   @Value("${app.token-header-name}") String tokenHeaderName) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenHeaderName = tokenHeaderName;
    }

    /**
     * ATK 생성 메서드
     *
     * @param principalDetails : 현재 접근중인 주체
     * @return ATK
     */
    public String generateAccessToken(PrincipalDetails principalDetails) {
        return generateToken(principalDetails, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    /**
     * RTK 생성 메서드
     *
     * @param principalDetails : 현재 접근중인 주체
     * @return RTK
     */
    public String generateRefreshToken(PrincipalDetails principalDetails) {
        return generateToken(principalDetails, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    /**
     * Request Header로부터 Token 추출 메서드
     *
     * @param request : HttpServletRequest
     * @return token
     */
    public String resolveToken(HttpServletRequest request) {
        return resolveToken(request.getHeader(tokenHeaderName));
    }

    /**
     * Bearer Token 에서 Token 추출 메서드
     *
     * @param token : Bearer Token
     * @return token : AccessToken
     */
    public String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return null;
    }

    /**
     * Token으로부터 Authentication 추출 메서드
     *
     * @param token : ATK or RTK
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<GrantedAuthority> authorities = getAuthorities(claims);

        PrincipalDetails principal = PrincipalDetails.builder()
                                                     .id(Long.parseLong(claims.get(UID_CLAIM_KEY, String.class)))
                                                     .authorities(authorities)
                                                     .build();
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * 토큰 유효성 검사 메서드
     *
     * @param token : ATK or RTK
     */
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.debug("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.debug("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.debug("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.debug("잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    /**
     * 만료시간 조회 메서드
     * */
    public long getRefreshTokenExpirationTime() {
        return REFRESH_TOKEN_EXPIRATION_TIME;
    }

    /**
     * 토큰 생성 메서드
     *
     * @param principalDetails : 현재 접근중인 주체
     * @param expirationTime : 만료 시간
     * @return Jwt Token
     */
    private String generateToken(PrincipalDetails principalDetails, long expirationTime) {
        String authorities = getAuthorities(principalDetails);
        return Jwts.builder()
                   .claim(UID_CLAIM_KEY, principalDetails.getId())
                   .claim(AUTHORITIES_CLAIM_KEY, authorities)
                   .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(this.key, SignatureAlgorithm.HS512)
                   .compact();
    }

    /**
     * Token을 이용해 JWT 만들어 보면서 유효성을 검사합니다.
     *
     * @param token : ATK or RTK
     */
    private Claims getClaims(String token)
            throws SecurityException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    /**
     * Claims로 부터 Authorities 추출 메서드
     *
     * @param claims : Token 의 클레임(정보 조각)
     * @return Collection<? extends GrantedAuthority>
     */
    private List<GrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get(AUTHORITIES_CLAIM_KEY, String.class).split(","))
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }

    /**
     * principalDetails 로부터 Authorities 추출 메서드
     *
     * @param principalDetails : 현재 접근중인 주체
     * @return authorities to String
     */
    private String getAuthorities(PrincipalDetails principalDetails) {
        return principalDetails.getAuthorities()
                               .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }

}