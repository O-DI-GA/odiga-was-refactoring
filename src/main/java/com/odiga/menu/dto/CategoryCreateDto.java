package com.odiga.menu.dto;

import com.odiga.menu.entity.Category;
import com.odiga.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "Owner 카테고리 등록 요청")
public record CategoryCreateDto(

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    @Schema(description = "메뉴 카테고리 이름", example = "메인 메뉴")
    String categoryName) {

    public Category toEntity(Store store) {
        return Category.builder()
            .name(categoryName)
            .store(store)
            .build();
    }

}
