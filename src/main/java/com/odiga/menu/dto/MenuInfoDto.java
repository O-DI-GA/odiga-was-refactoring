package com.odiga.menu.dto;

import com.odiga.menu.entity.Menu;

public record MenuInfoDto(Long menuId, String menuName, int price, String imageUrl) {

    public static MenuInfoDto from(Menu menu) {
        return new MenuInfoDto(menu.getId(), menu.getName(), menu.getPrice(), menu.getTitleImageUrl());
    }
}
