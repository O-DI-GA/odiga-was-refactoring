package com.odiga.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.dto.StoreRegisterRequestDto;
import com.odiga.store.dto.StoreResponseDto;
import com.odiga.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class OwnerStoreServiceTest {

    @InjectMocks
    OwnerStoreService ownerStoreService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    OwnerStoreImageService ownerStoreImageService;

    @Test
    void registerStoreTest() {
        StoreRegisterRequestDto storeRegisterRequestDto =
            new StoreRegisterRequestDto("store", "000-0000-0000", "경상북도 경산시", 0.00, 0.00);

        StoreResponseDto response = ownerStoreService.registerStore(new Owner(), storeRegisterRequestDto, new MockMultipartFile("titleImage", new byte[0]), new ArrayList<>());

        assertThat(response.name()).isEqualTo("store");

        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    void findAllOwnerTest() {
        Owner owner = new Owner();

        Store store = Store.builder()
            .name("store1")
            .owner(owner)
            .build();

        Store store2 = Store.builder()
            .name("store2")
            .owner(owner)
            .build();

        List<Store> stores = new ArrayList<>(List.of(store, store2));

        when(storeRepository.findByOwnerId(any())).thenReturn(stores);

        List<StoreResponseDto> storeResponse = ownerStoreService.findAllStoreByOwner(owner);

        assertThat(storeResponse.size()).isEqualTo(stores.size());
        assertThat(storeResponse.get(0).name()).isEqualTo(stores.get(0).getName());
    }
}