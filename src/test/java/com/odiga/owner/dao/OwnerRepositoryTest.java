package com.odiga.owner.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.global.config.JpaConfig;
import com.odiga.owner.entity.Owner;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    Owner owner;

    @BeforeEach
    void init() {
        owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("owner")
            .build();

        ownerRepository.save(owner);

    }

    @Test
    @DisplayName("Owner 생성 테스트(성공)")
    void createOwnerTest() {

        Owner findOwner = ownerRepository.findById(owner.getId()).orElseThrow();

        assertThat(findOwner.getId()).isEqualTo(owner.getId());
        assertThat(owner.getCreatedAt()).isNotNull();
        assertThat(owner.getUpdateAt()).isNotNull();
    }

    @Test
    @DisplayName("Owner 생성 테스트(실패 - email 누락)")
    void creatOwnerTestFailByNullEmail() {
        Owner owner = Owner.builder()
            .password("password")
            .name("owner")
            .build();

        assertThatThrownBy(() -> ownerRepository.save(owner)).isInstanceOf(
            DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Owner 생성 테스트(실패 - email 중복)")
    void creatOwnerTestFailByConfEmailConflict() {

        Owner owner2 = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("owner")
            .build();

        assertThatThrownBy(() -> ownerRepository.save(owner2)).isInstanceOf(
            DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("존재하는 이메일로 Owner 찾기 - existBy")
    void existByEmailOwnerTest() {
        String email = "example@google.com";

        boolean exists = ownerRepository.existsByEmail(email);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 Owner 찾기 - existBy")
    void existByEmailOwnerFailTest() {
        String email = "example2@google.com";

        boolean exists = ownerRepository.existsByEmail(email);

        assertThat(exists).isFalse();
    }


    @Test
    @DisplayName("존재하는 이메일로 Owner 찾기")
    void findByEmailOwnerTest() {

        String email = owner.getEmail();

        Optional<Owner> findOwner = ownerRepository.findByEmail(email);

        assertThat(findOwner.isPresent()).isTrue();
        assertThat(findOwner.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("존재하는 않는 이메일로 Owner 찾기")
    void findByEmailOwnerTestFail() {
        String findEmail = "example@naver.com";

        Optional<Owner> findOwner = ownerRepository.findByEmail(findEmail);

        assertThat(findOwner.isEmpty()).isTrue();
    }
}