package com.odiga.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.common.type.Role;
import com.odiga.owner.application.OwnerUserDetailsService;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    OwnerUserDetailsService ownerUserDetailsService;

    @Autowired
    OwnerRepository ownerRepository;

    Owner owner;

    @BeforeEach
    void init() {
        String secretKey = "secretKeySecretKeySecretKeySecretKeySecretKeysecretKeySecretKeySecretKeySecretKeySecretKey";
        jwtTokenProvider = new JwtTokenProvider(secretKey, 1000, ownerUserDetailsService);
        owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .build();
    }

    @Test
    void creatTokenTest() {
        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");

        assertThat(token.accessToken()).isNotNull();
        assertThat(token.refreshToken()).isNotNull();
    }

    @Test
    @DisplayName("유효 기간이 지난 access token의 claims 조회한 경우 예외를 발생")
    void expireTimeTest() throws InterruptedException {
        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");
        Thread.sleep(2000);

        assertThatThrownBy(() -> jwtTokenProvider.getClaims(token.accessToken())).isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("올바른 Owner email으로 authentication을 조회하면 OWNER 권한을 가진다")
    void getOwnerAuthenticationTest() {

        ownerRepository.save(owner);

        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");

        Authentication authentication = jwtTokenProvider.getAuthentication(token.accessToken());

        GrantedAuthority grantedAuthority = authentication.getAuthorities().stream().findFirst()
            .orElse(null);

        assertThat(grantedAuthority).isNotNull();
        assertThat(grantedAuthority.toString()).isEqualTo(Role.ROLE_OWNER.name());
    }

    @Test
    @DisplayName("존재하지 않는 Owner email으로 authentication를 조회하면 UsernameNotFoundException 발생")
    void notExistOwnerEmailGetAuthenticationTest() {
        JwtTokenDto token = jwtTokenProvider.createToken("example@naver.com");

        assertThatThrownBy(() -> jwtTokenProvider.getAuthentication(token.accessToken()))
            .isInstanceOf(UsernameNotFoundException.class);
    }
}