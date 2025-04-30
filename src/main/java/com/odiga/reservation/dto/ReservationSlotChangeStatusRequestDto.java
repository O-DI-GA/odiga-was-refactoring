package com.odiga.reservation.dto;

import com.odiga.reservation.entity.ReservationSlotStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "예약 슬록 상태 변경 요청")
public record ReservationSlotChangeStatusRequestDto(
    @Schema(description = "변경 할 예약 슬롯 상태", example = "UNAVAILABLE")
    ReservationSlotStatus status) {

}
