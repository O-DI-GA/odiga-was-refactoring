package com.odiga.reservation.dao;

import com.odiga.reservation.entity.ReservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long>,
    ReservationSlotQueryRepository, ReservationSlotJdbcRepository {

}
