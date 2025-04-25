package com.odiga.menu.dto;

import com.odiga.menu.entity.Category;

public record CategoryInfoDto(Long categoryId, String categoryName) {


    public static CategoryInfoDto from(Category category) {
        return new CategoryInfoDto(category.getId(), category.getName());
    }

}
