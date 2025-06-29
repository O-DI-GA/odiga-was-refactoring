package com.odiga.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.config.TestConfig;
import com.odiga.owner.application.OwnerUserDetailsService;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(TestConfig.class)
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
        jwtTokenProvider = new JwtTokenProvider(secretKey, 1000);
        owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .build();
    }

    @AfterEach
    void clear() {
        ownerRepository.deleteAll();
    }

    @Test
    void creatTokenTest() {
        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");

        assertThat(token.accessToken()).isNotNull();
        assertThat(token.refreshToken()).isNotNull();
    }

    @Test
    @DisplayName("유효 기간이 지나지 않은 access token의 claims 조회한 경우 claims를 정상적으로 조회한다")
    void expireTimeTest() {

        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");
        Claims claims = jwtTokenProvider.getClaims(token.accessToken());

        assertThat(claims).isNotNull();
    }

    @Test
    @DisplayName("유효 기간이 지난 access token의 claims 조회한 경우 예외를 발생")
    void expireTimeTestFail() throws InterruptedException {
        JwtTokenDto token = jwtTokenProvider.createToken("example@google.com");
        Thread.sleep(2000);

        assertThatThrownBy(() -> jwtTokenProvider.getClaims(token.accessToken())).isInstanceOf(ExpiredJwtException.class);
    }
}