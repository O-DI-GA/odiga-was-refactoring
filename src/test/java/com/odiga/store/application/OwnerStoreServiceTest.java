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
import com.odiga.store.enums.StoreStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        List<Store> storeList = List.of(store, store2);
        Page<Store> stores = new PageImpl<>(storeList);

        Pageable pageable = PageRequest.of(0, 10);

        when(storeRepository.findByOwnerId(any(), any())).thenReturn(stores);

        List<StoreResponseDto> storeResponse = ownerStoreService.findAllStoreByOwner(owner, pageable);

        assertThat(storeResponse.size()).isEqualTo(stores.getSize());
        assertThat(storeResponse.get(0).name()).isEqualTo(storeList.get(0).getName());
    }

    @Test
    @DisplayName("가게 상태를 영업중으로 바꾼다")
    void storeOpenTest() {
        Long id = 1L;

        Owner owner = Owner.builder()
            .id(id)
            .build();

        Store store = Store.builder()
            .id(id)
            .owner(owner)
            .build();

        when(storeRepository.findById(id)).thenReturn(Optional.of(store));
        StoreResponseDto storeResponseDto = ownerStoreService.openStore(owner, id);

        assertThat(storeResponseDto.storeStatus()).isEqualTo(StoreStatus.OPEN);
    }

    @Test
    @DisplayName("가게 상태를 영업종료로 바꾼다")
    void storeCloseTest() {
        Long id = 1L;

        Owner owner = Owner.builder()
            .id(id)
            .build();

        Store store = Store.builder()
            .id(id)
            .storeStatus(StoreStatus.OPEN)
            .owner(owner)
            .build();

        when(storeRepository.findById(id)).thenReturn(Optional.of(store));
        StoreResponseDto storeResponseDto = ownerStoreService.closeStore(owner, id);

        assertThat(storeResponseDto.storeStatus()).isEqualTo(StoreStatus.ClOSE);
    }
}