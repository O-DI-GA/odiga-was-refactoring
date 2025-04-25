package com.odiga.menu.controller;

import com.odiga.menu.api.OwnerCategoryApi;
import com.odiga.menu.application.OwnerCategoryService;
import com.odiga.menu.dto.CategoryCreateDto;
import com.odiga.owner.entity.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/owners/stores/{storeId}/categories")
@RequiredArgsConstructor
public class OwnerCategoryController implements OwnerCategoryApi {

    private final OwnerCategoryService ownerCategoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@AuthenticationPrincipal Owner owner,
                                         @PathVariable Long storeId,
                                         @RequestBody CategoryCreateDto categoryCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ownerCategoryService.saveCategoryByStoreId(owner, storeId, categoryCreateDto));
    }

    @GetMapping
    public ResponseEntity<?> findAllCategoryByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok(ownerCategoryService.findAllCategoryByStoreId(storeId));
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategoryByCategoryId(@AuthenticationPrincipal Owner owner,
                                                        @PathVariable Long storeId,
                                                        @PathVariable Long categoryId) {
        ownerCategoryService.deleteCategoryByCategoryId(owner, storeId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
