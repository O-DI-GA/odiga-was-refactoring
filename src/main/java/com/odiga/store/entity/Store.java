package com.odiga.store.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.global.exception.CustomException;
import com.odiga.like.entity.LikeStore;
import com.odiga.menu.entity.Category;
import com.odiga.owner.entity.Owner;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.review.entity.Review;
import com.odiga.store.enums.StoreStatus;
import com.odiga.store.exception.OwnerStoreErrorCode;
import com.odiga.table.entity.StoreTable;
import com.odiga.waiting.entity.Waiting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String address;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Owner owner;

    private String titleImageUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus = StoreStatus.ClOSE;

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<StoreTable> storeTables = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Waiting> waitingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<ReservationSlot> reservationSlots = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<LikeStore> likeStores = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<StoreImage> images = new ArrayList<>();

    public void storeOpen() {
        if (storeStatus.equals(StoreStatus.OPEN)) {
            throw new CustomException(OwnerStoreErrorCode.ALREADY_OPEN);
        }
        this.storeStatus = StoreStatus.OPEN;
    }

    public void storeClose() {
        if (storeStatus.equals(StoreStatus.ClOSE)) {
            throw new CustomException(OwnerStoreErrorCode.ALREADY_CLOSE);
        }
        this.storeStatus = StoreStatus.ClOSE;
    }

    public void createStoreImages(String titleImageUrl, List<StoreImage> storeImages) {
        this.titleImageUrl = titleImageUrl;
        this.images = storeImages;
    }

    public void validateOwner(Long ownerId) {
        if (!owner.getId().equals(ownerId)) {
            throw new CustomException(OwnerStoreErrorCode.FORBIDDEN);
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.setStore(this);
    }

    public void addReservationSlot(ReservationSlot reservationSlot) {
        reservationSlots.add(reservationSlot);
        reservationSlot.setStore(this);
    }

    public void addStoreTable(StoreTable storeTable) {
        storeTables.add(storeTable);
        storeTable.setStore(this);
    }
}
