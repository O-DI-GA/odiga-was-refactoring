package com.odiga.reservation.dto;

import com.odiga.reservation.enums.WeekDay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Schema(title = "에약 슬롯 등록 요청")
public record ReservationSlotCreateRequestDto(

    @Schema(description = "예약 슬롯 생성 년", example = "2025")
    int year,

    @Min(value = 1, message = "월은 1 이상이어야 합니다.")
    @Max(value = 12, message = "월은 12 이하여야 합니다.")
    @Schema(description = "예약 슬롯 생성 월", example = "5")
    int month,
    List<DaySchedule> daySchedules) {

    public record DaySchedule(
        @NotNull(message = "요일은 필수입니다.")
        @Schema(description = "예약 슬롯 생성 요일", example = "MONDAY")
        WeekDay dayOfWeek,

        @NotNull(message = "시작 시간은 필수입니다.")
        @Schema(description = "예약 슬롯 생성 시작 시간", example = "09:00", type = "string")
        LocalTime startTime,

        @NotNull(message = "종료 시간은 필수입니다.")
        @Schema(description = "예약 슬롯 생성 끝 시간", example = "18:00", type = "string")
        LocalTime endTime,

        @Min(value = 1, message = "간격은 1분 이상이어야 합니다.")
        @Schema(description = "예약 슬롯 생성 시간 간격", example = "30")
        int interval) {

    }
}
