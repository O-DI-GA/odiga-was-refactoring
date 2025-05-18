package com.odiga.table.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import com.odiga.table.dao.StoreTableRepository;
import com.odiga.table.dto.StoreTableCreateRequestDto;
import com.odiga.table.dto.StoreTableResponseDto;
import com.odiga.table.entity.StoreTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerStoreTableServiceTest {

    @InjectMocks
    OwnerStoreTableService ownerStoreTableService;

    @Mock
    StoreTableRepository storeTableRepository;

    @Mock
    StoreRepository storeRepository;

    Owner owner = Owner.builder()
        .id(1L)
        .build();


    Store store = Store.builder()
        .id(1L)
        .owner(owner)
        .build();

    Long storeId = 1L;

    @Test
    void addStoreTableTest() {
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        StoreTableCreateRequestDto requestDto = new StoreTableCreateRequestDto(5, 1);

        StoreTableResponseDto storeTableResponseDto = ownerStoreTableService.addStoreTable(owner, storeId, requestDto);

        assertThat(storeTableResponseDto.tableNumber()).isEqualTo(requestDto.tableNumber());
        assertThat(storeTableResponseDto.maxSeat()).isEqualTo(requestDto.maxSeat());

        verify(storeTableRepository, times(1)).save(any(StoreTable.class));
    }

    @Test
    void findByStoreIdTest() {
        List<StoreTable> storeTables = new ArrayList<>(List.of(new StoreTable(), new StoreTable()));

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(storeTableRepository.findByStoreId(storeId)).thenReturn(storeTables);

        List<StoreTableResponseDto> result = ownerStoreTableService.findStoreTableByStoreId(owner, storeId);

        assertThat(result.size()).isEqualTo(storeTables.size());
    }
}
