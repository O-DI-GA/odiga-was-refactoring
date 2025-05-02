package com.odiga.reservation.dto;

import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.reservation.enums.ReservationSlotStatus;
import java.time.LocalDateTime;

public record ReservationSlotInfoDto(Long reservationSlotId, LocalDateTime reservationTime,
                                     ReservationSlotStatus status) {

    public static ReservationSlotInfoDto from(ReservationSlot reservationSlot) {
        return new ReservationSlotInfoDto(reservationSlot.getId(), reservationSlot.getReservationTime(), reservationSlot.getReservationSlotStatus());
    }
}
