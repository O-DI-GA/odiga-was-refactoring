package com.odiga.reservation.dto;

import java.time.LocalTime;
import java.util.List;

public record ReservationSlotCreateRequestDto(int year, int month, List<DaySchedule> daySchedules) {

    public record DaySchedule(String dayOfWeek, LocalTime startTime, LocalTime endTime, int interval) {

    }

}
