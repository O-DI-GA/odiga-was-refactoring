package com.odiga.menu.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.odiga.global.config.JpaConfig;
import com.odiga.menu.entity.Category;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerRepository ownerRepository;

    Store store;

    Category category;

    @BeforeEach
    void init() {
        Owner owner = Owner.builder()
            .email("example@google.com")
            .build();

        ownerRepository.save(owner);

        store = Store.builder()
            .name("store")
            .owner(owner)
            .build();

        storeRepository.save(store);

        category = Category.builder()
            .name("category")
            .store(store)
            .build();

        categoryRepository.save(category);
    }

    @Test
    void findByStoreIdTest() {
        List<Category> categories = categoryRepository.findByStoreId(store.getId());

        assertThat(categories.size()).isEqualTo(1);
    }

}