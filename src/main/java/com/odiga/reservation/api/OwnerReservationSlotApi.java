package com.odiga.reservation.api;

import com.odiga.owner.entity.Owner;
import com.odiga.reservation.dto.ReservationSlotChangeStatusRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Owner Reservation Slot API", description = "Owner Reservation Slot 관련 API")
public interface OwnerReservationSlotApi {

    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 등록 성공", value = """
                [
                    {
                        "reservationSlotId": 1,
                        "reservationTime": "2025-04-30 20:10:00",
                        "status": "AVAILABLE"
                    },
                    {
                        "reservationSlotId": 2,
                        "reservationTime": "2025-04-09 20:10:00",
                        "status": "AVAILABLE"
                      },
                      {
                        "reservationSlotId": 3,
                        "reservationTime": "2025-04-23 20:10:00",
                        "status": "AVAILABLE"
                      },
                      {
                        "reservationSlotId": 4,
                        "reservationTime": "2025-04-16 20:10:00",
                        "status": "AVAILABLE"
                      },
                      {
                        "reservationSlotId": 5,
                        "reservationTime": "2025-04-02 20:10:00",
                        "status": "AVAILABLE"
                      }
                ]
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 삭제 실패", value = """
                {
                  "message": "존재하지 않는 가게 입니다."
                }
                """),}))})
    ResponseEntity<?> addReservationSlot(@AuthenticationPrincipal Owner owner,
                                         @PathVariable Long storeId,
                                         @RequestBody ReservationSlotCreateRequestDto requestDto);

    @ApiResponses({
        @ApiResponse(responseCode = "204", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 삭제 실패", value = """
                {
                  "message": "존재하지 않는 예약 슬롯 입니다."
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 삭제 실패", value = """
                {
                  "message": "존재하지 않는 가게 입니다."
                }
                """),})),})
    ResponseEntity<?> deleteReservationSlot(@AuthenticationPrincipal Owner owner,
                                            @PathVariable Long storeId,
                                            @PathVariable Long reservationSlotId);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 상태 변경 성공", value = """
                {
                    "reservationSlotId": 1,
                    "reservationTime": "2025-04-30 20:10:00",
                    "status": "UNAVAILABLE"
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 삭제 실패", value = """
                {
                  "message": "존재하지 않는 예약 슬롯 입니다."
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "예약 슬롯 삭제 실패", value = """
                {
                  "message": "존재하지 않는 가게 입니다."
                }
                """),}))})
    ResponseEntity<?> changeStatusSlot(@AuthenticationPrincipal Owner owner,
                                       @PathVariable Long storeId,
                                       @PathVariable Long reservationSlotId,
                                       @RequestBody ReservationSlotChangeStatusRequestDto requestDto);
}
