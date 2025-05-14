package com.odiga.store.api;

import com.odiga.owner.entity.Owner;
import com.odiga.store.dto.StoreRegisterRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Owner Store API", description = "Owner Store 관련 API")
public interface OwnerStoreApi {


    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 등록 성공", value = """
                {
                    "success": true,
                    "data": {
                      "storeId": 1,
                      "name": "가게 이름",
                      "phoneNumber": "02-0000-0000",
                      "address": "경상북도 경산시 대학로",
                      "titleImageUrl": "https://example.com/image.jpg",
                      "storeStatus": "ClOSE"
                    }
                }
                """),})),})
    ResponseEntity<?> registerOwnerStore(@AuthenticationPrincipal Owner owner,
                                         @RequestPart StoreRegisterRequestDto storeRegisterRequestDto,
                                         @RequestPart MultipartFile storeTitleImage,
                                         @RequestPart List<MultipartFile> images);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 조회 성공", value = """
                {
                    "success": true,
                    "data": [
                        {
                          "storeId": 1,
                          "name": "가게 이름",
                          "phoneNumber": "02-0000-0000",
                          "address": "경상북도 경산시 대학로",
                          "titleImageUrl": "https://example.com/image.jpg",
                          "storeStatus": "ClOSE"
                        },
                        {
                          "storeId": 2,
                          "name": "가게 이름2",
                          "phoneNumber": "02-0000-0000",
                          "address": "경상북도 경산시 대학로",
                          "titleImageUrl": "https://example.com/image.jpg",
                          "storeStatus": "ClOSE"
                        }
                    ]
                }
                """),})),})
    ResponseEntity<?> findAllOwnerStore(@AuthenticationPrincipal Owner owner, Pageable pageable);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 상태 변경 성공", value = """
                {
                    "success": true,
                    "data": {
                      "storeId": 1,
                      "name": "가게 이름",
                      "phoneNumber": "02-0000-0000",
                      "address": "경상북도 경산시 대학로",
                      "titleImageUrl": "https://example.com/image.jpg",
                      "storeStatus": "OPEN"
                    }
                }
                """),})),})
    ResponseEntity<?> changeStoreStatusToOpen(@AuthenticationPrincipal Owner owner, @PathVariable Long storeId);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 상태 변경 성공", value = """
                {
                    "success": true,
                    "data": {
                      "storeId": 1,
                      "name": "가게 이름",
                      "phoneNumber": "02-0000-0000",
                      "address": "경상북도 경산시 대학로",
                      "titleImageUrl": "https://example.com/image.jpg",
                      "storeStatus": "CLOSE"
                    }
                }
                """),})),})
    ResponseEntity<?> changeStoreStatusToClose(@AuthenticationPrincipal Owner owner, @PathVariable Long storeId);
}
