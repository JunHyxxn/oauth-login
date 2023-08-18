package com.junhyxxn.back.common.filter;

import com.junhyxxn.back.common.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = jwtUtil.resolveToken(request);

        if (StringUtils.hasText(token) && jwtUtil.isValid(token)) {
            final Authentication authentication = jwtUtil.getAuthentication(token);
            // 인증 성공 시 SecurityContext에 Authentication 담아줍니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context 에 {} 인증 정보 저장했습니다.", authentication);
        }

        filterChain.doFilter(request, response);
    }

}
