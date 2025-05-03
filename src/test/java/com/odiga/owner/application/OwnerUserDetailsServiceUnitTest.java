package com.odiga.owner.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.odiga.common.enums.Role;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

@ExtendWith(MockitoExtension.class)
class OwnerUserDetailsServiceUnitTest {

    @InjectMocks
    OwnerUserDetailsService ownerUserDetailsService;

    @Mock
    OwnerRepository ownerRepository;

    Owner owner;

    @BeforeEach
    void init() {
        owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("name")
            .build();
    }

    @Test
    @DisplayName("Owner의 email로 owner를 조회")
    void findOwnerByUsernameTest() {

        when(ownerRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));

        Owner findOwner = ownerUserDetailsService.loadUserByUsername("example@google.com");

        GrantedAuthority grantedAuthority = findOwner.getAuthorities().stream().findFirst()
            .orElse(null);

        assertThat(findOwner.getUsername()).isEqualTo(owner.getUsername());
        assertThat(grantedAuthority).isNotNull();
        assertThat(grantedAuthority.toString()).isEqualTo(Role.ROLE_OWNER.name());
    }
}