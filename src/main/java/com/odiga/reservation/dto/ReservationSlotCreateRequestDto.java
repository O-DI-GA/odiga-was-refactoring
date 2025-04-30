package com.odiga.reservation.dto;

import com.odiga.reservation.entity.WeekDay;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Schema(title = "에약 슬롯 등록 요청")
public record ReservationSlotCreateRequestDto(

    @Schema(description = "예약 슬롯 생성 년", example = "2025")
    int year,
    @Schema(description = "예약 슬롯 생성 월", example = "5")
    int month,
    List<DaySchedule> daySchedules) {

    public record DaySchedule(
        @Schema(description = "예약 슬롯 생성 요일", example = "MONDAY")
        WeekDay dayOfWeek,
        @Schema(description = "예약 슬롯 생성 시작 시간", example = "09:00", type = "string")
        LocalTime startTime,
        @Schema(description = "예약 슬롯 생성 끝 시간", example = "18:00", type = "string")
        LocalTime endTime,
        @Schema(description = "예약 슬롯 생성 시간 간격", example = "30")
        int interval) {

    }
}
