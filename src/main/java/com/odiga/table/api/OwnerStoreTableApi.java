package com.odiga.table.api;

import com.odiga.owner.entity.Owner;
import com.odiga.table.dto.StoreTableCreateRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Owner Store Table API", description = "Owner Store Table 관련 API")
public interface OwnerStoreTableApi {

    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 테이블 등록 성공", value = """
                {
                    "success": true,
                    "data": {
                        "id": 1,
                        "tableNumber": 1,
                        "maxSeat": 5,
                        "status": "EMPTY"
                    }
                
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 테이블 등록 삭제 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 가게 입니다."
                    }
                }
                """),})),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 테이블 등록 삭제 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "이미 존재하는 테이블 번호 입니다."
                    }
                }
                """),}))})
    ResponseEntity<?> addStoreTable(@AuthenticationPrincipal Owner owner,
                                    @PathVariable Long storeId,
                                    @RequestBody StoreTableCreateRequestDto storeTableCreateRequestDto);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 조회 성공", value = """
                {
                    "success": true,
                    "data": [
                        {
                            "id": 1,
                            "tableNumber": 1,
                            "maxSeat": 5,
                            "status": "EMPTY"
                        },
                        {
                            "id": 2,
                            "tableNumber": 2,
                            "maxSeat": 5,
                            "status": "EMPTY"
                        },
                        {
                            "id": 3,
                            "tableNumber": 3,
                            "maxSeat": 5,
                            "status": "EMPTY"
                        },
                        {
                            "id": 4,
                            "tableNumber": 4,
                            "maxSeat": 5,
                            "status": "EMPTY"
                        }
                    ]
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "가게 테이블 조회 실패", value = """
                {
                    "success": false,
                    "error": {
                        "message": "존재하지 않는 가게 입니다."
                    }
                }
                """),}))})
    ResponseEntity<?> findByStoreId(@AuthenticationPrincipal Owner owner, @PathVariable Long storeId);

}
