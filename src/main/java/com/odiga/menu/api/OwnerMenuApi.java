package com.odiga.menu.api;

import com.odiga.menu.dto.MenuCreateDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Owner Menu API", description = "Owner 메뉴 관련 API")
public interface OwnerMenuApi {

    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "메뉴 생성 성공", value = """
                {
                  "menuId": 1,
                  "menuId": "메뉴",
                  "price" : 10000,
                  "imageUrl" : "https://example.com/image.jpg"
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "메뉴 생성 실패", value = """
                {
                  "message": "존재하지 않는 카테고리 입니다."
                }
                """),}))})
    ResponseEntity<?> saveMenu(@PathVariable Long categoryId,
                               @RequestPart MultipartFile menuImage,
                               @RequestPart MenuCreateDto menuCreateDto);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "메뉴 조회 성공", value = """
                [
                    {
                      "menuId": 1,
                      "menuId": "메뉴",
                      "price" : 10000,
                      "imageUrl" : "https://example.com/image.jpg"
                    },
                    {
                      "menuId": 2,
                      "menuId": "메뉴2",
                      "price" : 20000,
                      "imageUrl" : "https://example.com/image2.jpg"
                    }
                ]
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "메뉴 조회 실패", value = """
                {
                  "message": "존재하지 않는 카테고리 입니다."
                }
                """),}))})
    ResponseEntity<?> findMenuByCategoryId(@PathVariable Long categoryId);

    @ApiResponses({
        @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "메뉴 삭제 실패", value = """
                {
                  "message": "존재하지 않는 메뉴 입니다."
                }
                """),})),})
    ResponseEntity<?> deleteMenuById(@PathVariable Long categoryId,
                                     @PathVariable Long menuId);
}
