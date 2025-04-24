package com.odiga.menu.application;

import com.odiga.global.exception.CustomException;
import com.odiga.menu.dao.CategoryRepository;
import com.odiga.menu.dto.CategoryCreateDto;
import com.odiga.menu.dto.CategoryInfoDto;
import com.odiga.menu.entity.Category;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.exception.StoreErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerCategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;


    @Transactional
    public CategoryInfoDto saveCategoryByStoreId(Owner owner, Long storeId, CategoryCreateDto categoryCreateDto) {

        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        Category category = Category.builder()
            .name(categoryCreateDto.categoryName())
            .store(store)
            .build();

        categoryRepository.save(category);

        store.addCategory(category);

        return CategoryInfoDto.from(category);
    }
}
