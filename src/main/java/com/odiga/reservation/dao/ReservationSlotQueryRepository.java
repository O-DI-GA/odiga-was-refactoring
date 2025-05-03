package com.odiga.reservation.dao;

import com.odiga.reservation.entity.ReservationSlot;
import java.time.LocalDate;
import java.util.List;

public interface ReservationSlotQueryRepository {

    List<ReservationSlot> findByStoreIdAndBetweenReservationTime(Long storeId,
                                                                 LocalDate startTime,
                                                                 LocalDate endTime);
}
