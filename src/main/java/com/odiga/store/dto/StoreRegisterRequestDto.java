package com.odiga.store.dto;

import com.odiga.owner.entity.Owner;
import com.odiga.store.entity.Store;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public record StoreRegisterRequestDto(String storeName, String phoneNumber, String address,
                              int tableCount, double longitude, double latitude) {

    public Store toEntity(Owner owner) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point location = geometryFactory.createPoint(new Coordinate(this.longitude, this.latitude));

        return Store.builder()
            .owner(owner)
            .name(this.storeName)
            .phoneNumber(this.phoneNumber)
            .address(this.address)
            .location(location)
            .build();
    }
}
