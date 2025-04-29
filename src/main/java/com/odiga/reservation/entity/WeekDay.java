package com.odiga.reservation.entity;

import com.odiga.global.exception.CustomException;
import com.odiga.global.exception.GlobalErrorCode;
import java.time.DayOfWeek;
import java.util.Arrays;

public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayOfWeek of(String dayOfWeek) {
        return DayOfWeek.valueOf(Arrays.stream(values())
            .filter(day -> day.name().equals(dayOfWeek.toUpperCase()))
            .findFirst()
            .orElseThrow(() -> new CustomException(GlobalErrorCode.BAD_REQUEST)).name());
    }
}
