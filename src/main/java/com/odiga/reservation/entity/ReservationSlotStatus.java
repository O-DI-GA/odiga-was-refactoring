package com.odiga.reservation.entity;

import com.odiga.global.exception.CustomException;
import com.odiga.global.exception.GlobalErrorCode;
import java.util.Arrays;

public enum ReservationSlotStatus {
    AVAILABLE,
    UNAVAILABLE;

    public static ReservationSlotStatus of(String status) {
        return Arrays.stream(values())
            .filter((s -> s.name().equals(status.toUpperCase())))
            .findFirst().orElseThrow(() -> new CustomException(GlobalErrorCode.BAD_REQUEST));
    }
}
