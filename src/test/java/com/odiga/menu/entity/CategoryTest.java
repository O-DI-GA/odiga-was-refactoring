package com.odiga.menu.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class CategoryTest {


    @Test
    void addMenuTest() {
        Category category = new Category();
        Menu menu = new Menu();

        category.addMenu(menu);

        List<Menu> menus = category.getMenus();

        assertThat(menus.size()).isEqualTo(1);
    }
}