package com.odiga.user.application;

import com.odiga.common.enums.Role;
import com.odiga.user.dao.UserRepository;
import com.odiga.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    User user;

    @BeforeEach
    void init() {
        user = User.builder().email("example@google.com").password("password").nickname("name").build();
    }

    @Test
    @DisplayName("User의 email로 User를 조회")
    void findOwnerByUsernameTest() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User findUser = customUserDetailsService.loadUserByUsername("example@google.com");

        GrantedAuthority grantedAuthority = findUser.getAuthorities().stream().findFirst().orElse(null);

        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(grantedAuthority).isNotNull();
        assertThat(grantedAuthority.toString()).isEqualTo(Role.ROLE_USER.name());
    }
}