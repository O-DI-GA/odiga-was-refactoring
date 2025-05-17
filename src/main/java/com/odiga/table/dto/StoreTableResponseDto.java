package com.odiga.table.dto;

import com.odiga.table.entity.StoreTable;
import com.odiga.table.enums.TableStatus;

public record StoreTableResponseDto(
    Long id,
    int tableNumber,
    int maxSeat,
    TableStatus status
) {

    public static StoreTableResponseDto from(StoreTable storeTable) {
        return new StoreTableResponseDto(storeTable.getId(), storeTable.getTableNumber(), storeTable.getMaxSeat(), storeTable.getTableStatus());
    }
}
