package com.odiga.global.config;

import com.odiga.global.filter.JwtAuthenticationFilter;
import com.odiga.global.filter.JwtExceptionFilter;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.application.OwnerUserDetailsService;
import com.odiga.user.application.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain ownerFilterChain(HttpSecurity http)
        throws Exception {
        http.securityMatcher("api/v1/owners/**")

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/owners/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(header -> header.frameOptions(FrameOptionsConfig::sameOrigin))

            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)

            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authenticationProvider(ownerAuthenticationProvider())

            .addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, ownerUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http)
        throws Exception {
        http.securityMatcher("api/v1/users/**")

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/users/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(header -> header.frameOptions(FrameOptionsConfig::sameOrigin))

            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)

            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authenticationProvider(userAuthenticationProvider())

            .addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider ownerAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(ownerUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
