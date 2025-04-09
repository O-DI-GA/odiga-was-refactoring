package com.odiga.store;

import com.odiga.owner.entity.Owner;
import com.odiga.store.dto.StoreRegisterRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Owner Store API", description = "Owner Store 관련 API")
public interface OwnerStoreApi {


    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 등록 성공", value = """
                {
                  "name": "가게 이름",
                  "phoneNumber": "02-0000-0000",
                  "address": "경상북도 경산시 대학로",
                  "titleImageUrl": "https://example.com/image.jpg"
                }
                """),})),})
    ResponseEntity<?> registerOwnerStore(@AuthenticationPrincipal Owner owner,
                                         @RequestPart StoreRegisterRequestDto storeRegisterRequestDto,
                                         @RequestPart MultipartFile storeTitleImage,
                                         @RequestPart List<MultipartFile> images);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 조회 성공", value = """
                [
                    {
                      "name": "가게 이름",
                      "phoneNumber": "02-0000-0000",
                      "address": "경상북도 경산시 대학로",
                      "titleImageUrl": "https://example.com/image.jpg"
                    },
                    {
                      "name": "가게 이름2",
                      "phoneNumber": "02-0000-0000",
                      "address": "경상북도 경산시 대학로",
                      "titleImageUrl": "https://example.com/image.jpg"
                    }
                ]
                """),})),})
    ResponseEntity<?> findAllOwnerStore(@AuthenticationPrincipal Owner owner);
}
