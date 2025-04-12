package com.odiga.store.dto;

import com.odiga.owner.entity.Owner;
import com.odiga.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Schema(title = "Owner Store 등록 요청")
public record StoreRegisterRequestDto(

    @Schema(description = "등록할 가게 이름", example = "정문반점")
    String storeName,
    @Schema(description = "등록할 가게 번호", example = "02-0000-0000")
    String phoneNumber,
    @Schema(description = "등록할 가게 주소", example = "경상북도 경산시 대학로")
    String address,
    @Schema(description = "등록할 가게 경도", example = "35.8334")
    double longitude,
    @Schema(description = "등록할 가게 위도", example = "128.7574")
    double latitude) {

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
