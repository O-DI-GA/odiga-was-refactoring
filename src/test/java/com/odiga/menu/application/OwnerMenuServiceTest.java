package com.odiga.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.global.exception.CustomException;
import com.odiga.global.s3.application.S3ImageService;
import com.odiga.menu.dao.CategoryRepository;
import com.odiga.menu.dao.MenuRepository;
import com.odiga.menu.dto.MenuCreateDto;
import com.odiga.menu.dto.MenuInfoDto;
import com.odiga.menu.entity.Category;
import com.odiga.menu.entity.Menu;
import com.odiga.menu.exception.CategoryErrorCode;
import com.odiga.menu.exception.MenuErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class OwnerMenuServiceTest {

    @InjectMocks
    OwnerMenuService ownerMenuService;
    @Mock
    MenuRepository menuRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    S3ImageService s3ImageService;

    @Test
    @DisplayName("메뉴 저장 성공")
    void addMenuTest() {
        Long categoryId = 1L;
        Category category = Category.builder().build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        MenuCreateDto menuCreateDto = new MenuCreateDto("menu", 1000);
        MenuInfoDto result = ownerMenuService.addMenu(categoryId, new MockMultipartFile("menuImage", new byte[0]), menuCreateDto);

        assertThat(result.menuName()).isEqualTo(menuCreateDto.menuName());

        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    @DisplayName("메뉴 저장 실패 - 존재하지 않는 카테고리에 메뉴 추가")
    void addMenuFailTest() {
        Long categoryId = 1L;

        MenuCreateDto menuCreateDto = new MenuCreateDto("menu", 1000);

        assertThatThrownBy(() -> ownerMenuService.addMenu(categoryId, new MockMultipartFile("menuImage", new byte[0]), menuCreateDto))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", CategoryErrorCode.NOT_FOUND_CATEGORY);

        verify(menuRepository, times(0)).save(any(Menu.class));
    }

    @Test
    @DisplayName("카테고리로 메뉴 조회 성공")
    void findMenuByCategoryIdTest() {
        Long categoryId = 1L;
        List<Menu> menus = new ArrayList<>(List.of(Menu.builder().build(), Menu.builder().build()));

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(menuRepository.findByCategoryId(categoryId)).thenReturn(menus);

        List<MenuInfoDto> result = ownerMenuService.findMenuByCategoryId(categoryId);
        assertThat(result.size()).isEqualTo(menus.size());
    }

    @Test
    @DisplayName("카테고리로 메뉴 조회 실패 - 존재하지 않는 카테고리")
    void findMenuByCategoryIdFailTest() {
        Long categoryId = 1L;

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThatThrownBy(() -> ownerMenuService.findMenuByCategoryId(categoryId))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", CategoryErrorCode.NOT_FOUND_CATEGORY);
    }

    @Test
    @DisplayName("메뉴 삭제 성공")
    void deleteByIdTest() {
        Long menuId = 1L;
        Menu menu = Menu.builder().build();

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        ownerMenuService.deleteById(menuId);

        verify(menuRepository, times(1)).delete(menu);
    }

    @Test
    @DisplayName("메뉴 삭제 실패 - 존재하지 않는 메뉴")
    void deleteByIdFailTest() {
        Long menuId = 1L;
        Menu menu = Menu.builder().build();

        assertThatThrownBy(() -> ownerMenuService.deleteById(menuId))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", MenuErrorCode.NOT_FOUND_MENU);

        verify(menuRepository, times(0)).delete(menu);
    }
}
