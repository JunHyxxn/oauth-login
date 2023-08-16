package com.junhyxxn.back.common.config;

import com.junhyxxn.back.common.filter.JwtAuthenticationFilter;
import com.junhyxxn.back.common.handler.JwtAuthenticationEntryPoint;
import com.junhyxxn.back.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // web.ignoring() : 정적 리소스 파일 Security Filter 아예 타지 않도록 설정한다.
        return web -> web.ignoring()
                         .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // add Filter
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                // Spring Security - CORS
                .cors(cors ->
                              cors.configurationSource(corsConfigurationSource()))

                // JWT 를 사용한다해서 CSRF 를 무조건 disable로 하는 것은 맞지 않습니다.
                // 즉 csrfFilter와 관련된 부분도 추가 구현이 필요하지만,
                // 우선 로그인 위주로만 진행할 것이기 때문에 disable로 설정한다.
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf ->
//                              csrf.csrfTokenRepository(csrfTokenRepository()))

                // SessionManagement Policy - Stateless
                .sessionManagement(session ->
                                           session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Exception Handler
                .exceptionHandling(handler ->
                                           handler.authenticationEntryPoint(authenticationEntryPoint)
                                                   .accessDeniedHandler(accessDeniedHandler))

                // request url pattern에 따른 SecurityFilterChain 동작 여부
                .authorizeRequests(request ->
                                           request
                                                   .antMatchers("/login/**").permitAll()
                                                   .antMatchers("/oauth2/**").permitAll()
                                                   .antMatchers("/auth/**").permitAll()
                                                   .antMatchers("/api/**").authenticated()
                                                   .anyRequest().authenticated())

                // oauth2 login - 추후 설정 예정
                .oauth2Login();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        // 쿠키 요청을 허용한다(다른 도메인 서버에 인증하는 경우에만 사용해야하며, true 설정시 보안상 이슈가 발생할 수 있다)
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 허용할 origin 설정하면 됩니다.

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

}
