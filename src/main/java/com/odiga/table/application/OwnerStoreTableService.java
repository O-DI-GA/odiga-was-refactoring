package com.odiga.table.application;

import com.odiga.global.exception.CustomException;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.exception.StoreErrorCode;
import com.odiga.table.dao.StoreTableRepository;
import com.odiga.table.dto.StoreTableCreateRequestDto;
import com.odiga.table.dto.StoreTableResponseDto;
import com.odiga.table.entity.StoreTable;
import com.odiga.table.exception.StoreTableErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerStoreTableService {

    private final StoreTableRepository storeTableRepository;
    private final StoreRepository storeRepository;


    @Transactional
    public StoreTableResponseDto addStoreTable(Owner owner, Long storeId,
                                               StoreTableCreateRequestDto storeTableCreateRequestDto) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        if (storeTableRepository.existsByTableNumberAndStoreId(storeTableCreateRequestDto.tableNumber(), storeId)) {
            throw new CustomException(StoreTableErrorCode.ALREADY_EXIST);
        }

        store.validateOwner(owner.getId());

        StoreTable storeTable = StoreTable.builder()
            .tableNumber(storeTableCreateRequestDto.tableNumber())
            .maxSeat(storeTableCreateRequestDto.maxSeat())
            .build();

        storeTableRepository.save(storeTable);

        store.addStoreTable(storeTable);

        return StoreTableResponseDto.from(storeTable);
    }

    @Transactional(readOnly = true)
    public List<StoreTableResponseDto> findStoreTableByStoreId(Owner owner, Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        List<StoreTable> storeTables = storeTableRepository.findByStoreId(storeId);

        return storeTables.stream().map(StoreTableResponseDto::from).toList();
    }
}
