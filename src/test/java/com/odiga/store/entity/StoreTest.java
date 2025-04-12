package com.odiga.store.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.odiga.global.exception.CustomException;
import com.odiga.store.exception.OwnerStoreErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    @DisplayName("가게 상태를 영업중으로 바꾼다")
    void storeOpenTest() {
        Store store = new Store();

        store.storeOpen();
        assertThat(store.getStoreStatus()).isEqualTo(StoreStatus.OPEN);
    }

    @Test
    @DisplayName("가게 상태가 이미 영업중인 상태에서 영업중으로 바꿀수 없다")
    void storeOpenFailTest() {
        Store store = Store.builder()
            .storeStatus(StoreStatus.OPEN)
            .build();

        assertThatThrownBy(store::storeOpen)
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", OwnerStoreErrorCode.ALREADY_OPEN);
    }

    @Test
    @DisplayName("가게 상태를 영업종료로 바꾼다")
    void storeCloseTest() {
        Store store = Store.builder()
            .storeStatus(StoreStatus.OPEN)
            .build();

        store.storeClose();
        assertThat(store.getStoreStatus()).isEqualTo(StoreStatus.ClOSE);
    }

    @Test
    @DisplayName("가게 상태를 이미 영업종료인 상태에서 영업종료로 바꿀수 없다")
    void storeCloseTestFail() {
        Store store = new Store();

        assertThatThrownBy(store::storeClose)
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", OwnerStoreErrorCode.ALREADY_CLOSE);
    }

}