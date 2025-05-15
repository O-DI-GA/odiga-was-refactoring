package com.odiga.menu.dao;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.global.config.JpaConfig;
import com.odiga.global.exception.CustomException;
import com.odiga.menu.entity.Category;
import com.odiga.menu.entity.Menu;
import com.odiga.menu.exception.MenuErrorCode;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import jakarta.persistence.EntityManager;
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
class MenuRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    MenuRepository menuRepository;

    Category category;

    @BeforeEach
    void init() {
        Owner owner = Owner.builder()
            .email("example@google.com")
            .build();

        ownerRepository.save(owner);

        Store store = Store.builder()
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
    void cascadeDeleteTest() {
        Menu menu1 = Menu.builder()
            .name("menu1")
            .build();

        Menu menu2 = Menu.builder()
            .name("menu1")
            .build();

        category.addMenu(menu1);
        category.addMenu(menu2);

        menuRepository.saveAll(List.of(menu1, menu2));

        categoryRepository.delete(category);
        entityManager.flush();

        assertThatThrownBy(() -> menuRepository.findById(menu1.getId())
            .orElseThrow(() -> new CustomException(MenuErrorCode.NOT_FOUND_MENU)))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", MenuErrorCode.NOT_FOUND_MENU);

        assertThatThrownBy(() -> menuRepository.findById(menu2.getId())
            .orElseThrow(() -> new CustomException(MenuErrorCode.NOT_FOUND_MENU)))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", MenuErrorCode.NOT_FOUND_MENU);
    }
}