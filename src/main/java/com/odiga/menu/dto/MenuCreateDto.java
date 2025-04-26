package com.odiga.menu.dto;

import com.odiga.menu.entity.Category;
import com.odiga.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Owner 메뉴 등록 요청")
public record MenuCreateDto(
    @Schema(description = "등록할 메뉴 이름", example = "치킨")
    String menuName,

    @Schema(description = "등록할 메뉴 가격", example = "21900")
    int price) {

    public Menu toEntity(Category category, String imageUrl) {
        return Menu.builder()
            .name(menuName)
            .price(price)
            .titleImageUrl(imageUrl)
            .category(category)
            .build();
    }
}
