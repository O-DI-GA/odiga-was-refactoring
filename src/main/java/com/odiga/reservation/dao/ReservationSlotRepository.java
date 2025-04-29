package com.odiga.reservation.dao;

import com.odiga.reservation.entity.ReservationSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long>,
    ReservationSlotQueryRepository {

    List<ReservationSlot> findByStoreId(Long storeId);

}
