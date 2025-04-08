package com.odiga.owner.entity;

import com.odiga.store.entity.Store;
import org.junit.jupiter.api.BeforeEach;

class OwnerTest {

    Store store;

    @BeforeEach
    void initStore() {
        store = Store.builder()
            .name("store")
            .build();
    }

//    @Test
//    void addStoreTest() {
//
//        Owner owner = Owner.builder()
//            .email("example@google.com")
//            .password("password")
//            .name("example")
//            .build();
//
////        owner.addStore(store);
//
//        assertThat(owner.getStores().size()).isEqualTo(1);
//    }

}