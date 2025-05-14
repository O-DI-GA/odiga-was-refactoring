package com.odiga.menu.api;

import com.odiga.menu.dto.CategoryCreateDto;
import com.odiga.owner.entity.Owner;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Owner Category API", description = "Owner 메뉴 Category 관련 API")
public interface OwnerCategoryApi {

    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 생성 성공", value = """
                {
                    "success": true,
                    "data": {
                        "categoryId": 1,
                        "categoryName": "카테고리"
                    }
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 생성 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 가게 입니다."
                    }
                }
                """),}))})
    ResponseEntity<?> addCategory(@AuthenticationPrincipal Owner owner,
                                  @PathVariable Long storeId,
                                  @RequestBody CategoryCreateDto categoryCreateDto);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 조회 성공", value = """
                {
                    "success": true,
                    "data": [
                        {
                          "categoryId": 1,
                          "categoryName": "카테고리"
                        },
                        {
                          "categoryId": 2,
                          "categoryName": "카테고리2"
                        }
                    ]
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 조회 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 가게 입니다."
                    }
                }
                """),}))})
    ResponseEntity<?> findAllCategoryByStoreId(@PathVariable Long storeId);

    @ApiResponses({
        @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 삭제 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 가게 입니다."
                    }
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "카테고리 삭제 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 카테고리 입니다."
                    }
                }
                """),}))})
    ResponseEntity<?> deleteCategoryByCategoryId(@AuthenticationPrincipal Owner owner,
                                                 @PathVariable Long storeId,
                                                 @PathVariable Long categoryId);
}
