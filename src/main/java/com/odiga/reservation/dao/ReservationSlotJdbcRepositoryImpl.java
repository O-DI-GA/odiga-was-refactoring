package com.odiga.reservation.dao;

import com.odiga.reservation.entity.ReservationSlot;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class ReservationSlotJdbcRepositoryImpl implements ReservationSlotJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void saveAllBatch(List<ReservationSlot> reservationSlots) {
        String sql = "INSERT INTO RESERVATION_SLOT "
                     + "(RESERVATION_TIME, RESERVATION_SLOT_STATUS, STORE_ID, CREATED_AT, UPDATED_AT) "
                     + "VALUES (?, ?, ?, now(), now())";

        jdbcTemplate.batchUpdate(sql,
            reservationSlots,
            reservationSlots.size(),
            (PreparedStatement ps, ReservationSlot rs) -> {
                ps.setTimestamp(1, Timestamp.valueOf(rs.getReservationTime()));
                ps.setString(2, rs.getReservationSlotStatus().name());
                ps.setLong(3, rs.getStore().getId());
            });
    }
}
