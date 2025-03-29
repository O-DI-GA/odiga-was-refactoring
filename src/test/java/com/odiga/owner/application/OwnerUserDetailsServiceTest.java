package com.odiga.owner.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.odiga.common.type.Role;
import com.odiga.config.TestConfig;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;

@Import(TestConfig.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class OwnerUserDetailsServiceTest {

    @Autowired
    OwnerUserDetailsService ownerUserDetailsService;

    @Autowired
    OwnerRepository ownerRepository;

    @BeforeEach
    void init() {
        Owner owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("name")
            .build();

        ownerRepository.save(owner);
    }

    @AfterEach
    void clear() {
        ownerRepository.deleteAll();
    }

    @Test
    @DisplayName("")
    void findOwnerByUsernameTest() {
        Owner owner = ownerUserDetailsService.loadUserByUsername("example@google.com");

        GrantedAuthority grantedAuthority = owner.getAuthorities().stream().findFirst()
            .orElse(null);

        assertThat(grantedAuthority).isNotNull();
        assertThat(grantedAuthority.toString()).isEqualTo(Role.ROLE_OWNER.name());
    }
}