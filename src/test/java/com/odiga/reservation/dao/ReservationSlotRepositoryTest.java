package com.odiga.reservation.dao;

import com.odiga.global.config.JpaConfig;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.entity.Owner;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.reservation.entity.ReservationSlotStatus;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JpaConfig.class)
@DataJpaTest
class ReservationSlotRepositoryTest {

    @Autowired
    ReservationSlotRepository reservationSlotRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerRepository ownerRepository;

    Store store;

    @BeforeEach
    void init() {
        Owner owner = Owner.builder()
            .email("example@google.com")
            .build();

        ownerRepository.save(owner);

        store = Store.builder()
            .name("store")
            .owner(owner)
            .build();

        storeRepository.save(store);
    }

    @Test
    void saveReservationSlotTest() {
        ReservationSlot reservationSlot = ReservationSlot.builder().build();

        reservationSlotRepository.save(reservationSlot);

        Assertions.assertThat(reservationSlot.getReservationSlotStatus()).isEqualTo(ReservationSlotStatus.AVAILABLE);
    }

    @Test
    void findStoreIdAndBetweenReservationTimeTest() {
        int size = 5;

        LocalDateTime dateTime = LocalDate.EPOCH.atStartOfDay();

        for (int i = 0; i < size; i++) {
            ReservationSlot reservationSlot = ReservationSlot.builder()
                .store(store)
                .reservationTime(dateTime)
                .build();
            reservationSlotRepository.save(reservationSlot);
            dateTime = dateTime.plusDays(1);
        }

        List<ReservationSlot> reservationSlots = reservationSlotRepository.findByStoreIdAndBetweenReservationTime(store.getId(), LocalDate.EPOCH, dateTime.toLocalDate());

        Assertions.assertThat(reservationSlots.size()).isEqualTo(size);
    }

}