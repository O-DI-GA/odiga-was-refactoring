package com.odiga.reservation.entity;

import com.odiga.global.exception.CustomException;
import com.odiga.global.exception.GlobalErrorCode;
import com.odiga.store.entity.Store;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationSlotTest {

    @Test
    void validateStoreFailTest() {
        Store store = Store.builder()
            .id(1L)
            .build();

        ReservationSlot reservationSlot = ReservationSlot.builder()
            .store(store)
            .build();

        Assertions.assertThatThrownBy(() -> reservationSlot.validateStore(2L))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.BAD_REQUEST);
    }
}