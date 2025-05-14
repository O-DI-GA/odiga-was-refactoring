package com.odiga.menu.controller;

import com.odiga.common.dto.ApiResponse;
import com.odiga.menu.api.OwnerMenuApi;
import com.odiga.menu.application.OwnerMenuService;
import com.odiga.menu.dto.MenuCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/owners/categories/{categoryId}/menus")
@RequiredArgsConstructor
public class OwnerMenuController implements OwnerMenuApi {

    private final OwnerMenuService ownerMenuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveMenu(@PathVariable Long categoryId,
                                      @RequestPart MultipartFile menuImage,
                                      @Valid @RequestPart MenuCreateDto menuCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok(ownerMenuService.addMenu(categoryId, menuImage, menuCreateDto)));
    }

    @GetMapping
    public ResponseEntity<?> findMenuByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.ok(ownerMenuService.findMenuByCategoryId(categoryId)));
    }

    @DeleteMapping("{menuId}")
    public ResponseEntity<?> deleteMenuById(@PathVariable Long categoryId, @PathVariable Long menuId) {
        ownerMenuService.deleteById(menuId);
        return ResponseEntity.noContent().build();
    }

}
