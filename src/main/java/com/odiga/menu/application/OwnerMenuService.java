package com.odiga.menu.application;

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OwnerMenuService {

    private static final String DIR_NAME = "menu";

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final S3ImageService s3ImageService;

    @Transactional
    public MenuInfoDto saveMenu(Long categoryId, MultipartFile menuImage, MenuCreateDto menuCreateDto) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND_CATEGORY));

        String imageUrl = s3ImageService.uploadS3(DIR_NAME, menuImage);

        Menu menu = menuCreateDto.toEntity(category, imageUrl);
        category.addMenu(menu);

        menuRepository.save(menu);

        return MenuInfoDto.from(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuInfoDto> findMenuByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CustomException(CategoryErrorCode.NOT_FOUND_CATEGORY);
        }

        List<Menu> menus = menuRepository.findByCategoryId(categoryId);

        return menus.stream().map(MenuInfoDto::from).toList();
    }

    @Transactional
    public void deleteById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new CustomException(MenuErrorCode.NOT_FOUND_MENU));

        menuRepository.delete(menu);
    }
}
