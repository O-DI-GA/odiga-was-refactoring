package com.odiga.store.dto;


import com.odiga.store.entity.Store;

public record StoreResponseDto(String name, String phoneNumber, String address, String titleImageUrl) {

    public static StoreResponseDto from(Store store) {
        return new StoreResponseDto(store.getName(), store.getPhoneNumber(), store.getAddress(), store.getTitleImageUrl());
    }

}
