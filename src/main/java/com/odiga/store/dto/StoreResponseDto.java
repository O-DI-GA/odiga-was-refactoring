package com.odiga.store.dto;


import com.odiga.store.entity.Store;
import com.odiga.store.entity.StoreStatus;

public record StoreResponseDto(Long storeId, String name, String phoneNumber, String address,
                               String titleImageUrl, StoreStatus storeStatus) {

    public static StoreResponseDto from(Store store) {
        return new StoreResponseDto(store.getId(), store.getName(), store.getPhoneNumber(), store.getAddress(), store.getTitleImageUrl(), store.getStoreStatus());
    }

}
