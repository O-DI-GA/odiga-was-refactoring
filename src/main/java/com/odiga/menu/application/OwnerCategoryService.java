package com.odiga.menu.application;

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
import java.util.List;
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

        Category category = categoryCreateDto.toEntity(store);

        categoryRepository.save(category);

        store.addCategory(category);

        return CategoryInfoDto.from(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryInfoDto> findAllCategoryByStoreId(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new CustomException(StoreErrorCode.NOT_FOUND_STORE);
        }

        List<Category> categories = categoryRepository.findByStoreId(storeId);

        return categories.stream().map(CategoryInfoDto::from).toList();
    }

    @Transactional
    public void deleteCategoryByCategoryId(Owner owner, Long storeId, Long categoryId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        // TODO : category pk를 fk로 가지고 있는 menu의 cascade 전략 필요
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_CATEGORY));

        categoryRepository.delete(category);
    }
}
