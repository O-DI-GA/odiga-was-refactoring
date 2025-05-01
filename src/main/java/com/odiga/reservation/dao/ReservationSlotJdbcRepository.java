package com.odiga.reservation.dao;

import com.odiga.reservation.entity.ReservationSlot;
import java.util.List;

public interface ReservationSlotJdbcRepository {

    void saveAllBatch(List<ReservationSlot> reservationSlots);
}
