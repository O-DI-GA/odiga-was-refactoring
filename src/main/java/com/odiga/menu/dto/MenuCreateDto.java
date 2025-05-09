package com.odiga.menu.dto;

import com.odiga.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(title = "Owner 메뉴 등록 요청")
public record MenuCreateDto(

    @NotBlank(message = "메뉴 이름은 필수입니다.")
    @Schema(description = "등록할 메뉴 이름", example = "치킨")
    String menuName,

    @PositiveOrZero(message = "메뉴의 가격은 음수일 수 없습니다.")
    @NotBlank(message = "메뉴 가격은 필수입니다.")
    @Schema(description = "등록할 메뉴 가격", example = "21900")
    int price) {

    public Menu toEntity(String imageUrl) {
        return Menu.builder()
            .name(menuName)
            .price(price)
            .titleImageUrl(imageUrl)
            .build();
    }
}
