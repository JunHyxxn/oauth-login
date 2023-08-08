package com.junhyxxn.back.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // web.ignoring() : 정적 리소스 파일 Security Filter 아예 타지 않도록 설정한다.
        return web -> web.ignoring()
                         .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Spring Security - CORS
                .cors(cors ->
                              cors.configurationSource(corsConfigurationSource()))

                // CSRF - JWT 라 하더라도 쿠키에 담아 전달, 저장할 것이기 때문에 쿠키기반 CSRF Filter를 등록해줍니다.
                // 추후 소셜 로그인 과정 중 ignore 가 필요하다면 설정 추가 예정
                // 우선 disable로 성공 후 개선하면서 진행해보자!!
//                .csrf(csrf ->
//                    csrf.csrfTokenRepository(csrfTokenRepository()))
                // 우선 disable로 설정한다.
                .csrf(AbstractHttpConfigurer::disable)

                // SessionManagement Policy - Stateless
                .sessionManagement(session ->
                                           session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // request url pattern에 따른 SecurityFilterChain 동작 여부
                .authorizeRequests(request ->
                                           request.antMatchers("/login/**").permitAll()
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
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.addAllowedOrigin("*"); // 허용할 origin 설정하면 됩니다.

        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
    
}
