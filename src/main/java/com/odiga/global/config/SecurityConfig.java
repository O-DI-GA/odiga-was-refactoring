package com.odiga.global.config;

import com.odiga.global.filter.JwtAuthenticationFilter;
import com.odiga.global.filter.JwtExceptionFilter;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.application.OwnerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final OwnerUserDetailsService ownerUserDetailsService;

    @Bean
    public SecurityFilterChain ownerFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("api/v1/owners/**")

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/owners/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(header -> header.frameOptions(FrameOptionsConfig::sameOrigin))

            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)

            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 을 사용하지 않음
            )

            .userDetailsService(ownerUserDetailsService)
            .addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
