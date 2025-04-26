package com.odiga.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.global.exception.CustomException;
import com.odiga.menu.dao.CategoryRepository;
import com.odiga.menu.dto.CategoryCreateDto;
import com.odiga.menu.dto.CategoryInfoDto;
import com.odiga.menu.entity.Category;
import com.odiga.menu.exception.CategoryErrorCode;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.exception.StoreErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerCategoryServiceTest {


    @InjectMocks
    OwnerCategoryService ownerCategoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    StoreRepository storeRepository;

    Store store;

    Owner owner;

    Long storeId = 1L;

    @BeforeEach
    void init() {
        owner = Owner.builder()
            .id(1L)
            .build();

        store = Store.builder()
            .name("store")
            .owner(owner)
            .build();
    }

    @Test
    @DisplayName("카테고리 생성 성공")
    void saveCategoryTest() {
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category name");

        CategoryInfoDto categoryInfoDto = ownerCategoryService.saveCategoryByStoreId(owner, storeId, categoryCreateDto);

        assertThat(categoryInfoDto.categoryName()).isEqualTo(categoryCreateDto.categoryName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패 - 없는 가게")
    void saveCategoryFailTest() {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category name");

        assertThatThrownBy(() -> ownerCategoryService.saveCategoryByStoreId(owner, storeId, categoryCreateDto))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", StoreErrorCode.NOT_FOUND_STORE);
    }

    @Test
    @DisplayName("가게의 모든 카테고리 조회")
    void findAllCategoryByStoreIdTest() {
        when(storeRepository.existsById(storeId)).thenReturn(true);

        List<Category> categories = new ArrayList<>(List.of(
            Category.builder().name("category1").build(), Category.builder().name("category2").build()));

        when(categoryRepository.findByStoreId(storeId)).thenReturn(categories);

        List<CategoryInfoDto> result = ownerCategoryService.findAllCategoryByStoreId(storeId);

        assertThat(result.size()).isEqualTo(categories.size());
    }


    @Test
    @DisplayName("카테고리 삭제 성공")
    void deleteByIdTest() {
        Long categoryId = 1L;

        Category category = Category.builder()
            .id(categoryId)
            .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        ownerCategoryService.deleteCategoryByCategoryId(owner, storeId, categoryId);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 없는 가게")
    void deleteByIdFailNotFoundStoreTest() {
        assertThatThrownBy(() -> ownerCategoryService.deleteCategoryByCategoryId(owner, storeId, 1L))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", StoreErrorCode.NOT_FOUND_STORE);
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 없는 카테고리")
    void deleteByIdFailNotFoundCategoryTest() {
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        assertThatThrownBy(() -> ownerCategoryService.deleteCategoryByCategoryId(owner, storeId, 1L))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", CategoryErrorCode.NOT_FOUND_CATEGORY);
    }
}