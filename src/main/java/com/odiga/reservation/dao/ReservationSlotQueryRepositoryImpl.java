package com.odiga.reservation.dao;

import com.odiga.reservation.entity.QReservationSlot;
import com.odiga.reservation.entity.ReservationSlot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationSlotQueryRepositoryImpl implements ReservationSlotQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationSlot> findByStoreIdAndBetweenReservationTime(Long storeId,
                                                                        LocalDate startTime,
                                                                        LocalDate endTime) {
        QReservationSlot reservationSlot = QReservationSlot.reservationSlot;

        return jpaQueryFactory.selectFrom(reservationSlot)
            .where(reservationSlot.store.id.eq(storeId))
            .where(reservationSlot.reservationTime.between(startTime.atStartOfDay(), LocalDateTime.of(endTime, LocalTime.MAX)))
            .fetch();
    }

}
