package com.odiga.reservation.dto;

import com.odiga.reservation.enums.ReservationSlotStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(title = "예약 슬록 상태 변경 요청")
public record ReservationSlotChangeStatusRequestDto(

    @NotNull(message = "슬롯 상태는 필수 입니다.")
    @Schema(description = "변경 할 예약 슬롯 상태", example = "UNAVAILABLE")
    ReservationSlotStatus status) {

}
