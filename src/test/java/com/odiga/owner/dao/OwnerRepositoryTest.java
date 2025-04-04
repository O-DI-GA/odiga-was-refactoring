package com.odiga.owner.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.global.config.JpaConfig;
import com.odiga.owner.entity.Owner;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@Import(JpaConfig.class)
@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    @DisplayName("Owner 생성 테스트(성공)")
    void createOwnerTest() {
        Owner owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("owner")
            .build();

        ownerRepository.save(owner);

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

        Owner owner1 = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("owner")
            .build();

        Owner owner2 = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("owner")
            .build();

        ownerRepository.save(owner1);

        assertThatThrownBy(() -> ownerRepository.save(owner2)).isInstanceOf(
            DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("존재하는 이메일로 Owner 찾기 - existBy")
    void existByEmailOwnerTest() {
        String email = "example@google.com";

        Owner owner = Owner.builder()
            .email(email)
            .password("password")
            .name("owner1")
            .build();

        ownerRepository.save(owner);

        boolean exists = ownerRepository.existsByEmail(email);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 Owner 찾기 - existBy")
    void existByEmailOwnerFailTest() {
        String email = "example@google.com";

        boolean exists = ownerRepository.existsByEmail(email);

        assertThat(exists).isFalse();
    }



    @Test
    @DisplayName("존재하는 이메일로 Owner 찾기")
    void findByEmailOwnerTest() {
        String email = "example@google.com";

        Owner owner = Owner.builder()
            .email(email)
            .password("password")
            .name("owner1")
            .build();

        ownerRepository.save(owner);

        Optional<Owner> findOwner = ownerRepository.findByEmail(email);

        assertThat(findOwner.isPresent()).isTrue();
        assertThat(findOwner.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("존재하는 않는 이메일로 Owner 찾기")
    void findByEmailOwnerTestFail() {
        String email = "example@google.com";

        Owner owner = Owner.builder()
            .email(email)
            .password("password")
            .name("owner1")
            .build();

        ownerRepository.save(owner);

        String findEmail = "example@naver.com";

        Optional<Owner> findOwner = ownerRepository.findByEmail(findEmail);

        assertThat(findOwner.isEmpty()).isTrue();
    }

}