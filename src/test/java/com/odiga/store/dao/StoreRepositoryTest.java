package com.odiga.store.dao;

import static org.assertj.core.api.Assertions.*;

import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import com.odiga.store.entity.Store;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void pagingFindByOwnerIdTest() {

        Owner owner = Owner.builder().email("email@exampl.com").build();

        ownerRepository.save(owner);

        for (int i = 0; i < 30; i++) {
            storeRepository.save(Store.builder().name("store" + i).owner(owner).build());
        }

        Page<Store> store = storeRepository.findByOwnerId(owner.getId(), PageRequest.of(0, 10));

        assertThat(store.getSize()).isEqualTo(10);
    }

}