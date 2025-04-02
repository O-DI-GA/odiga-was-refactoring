package com.odiga.store.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.like.entity.LikeStore;
import com.odiga.menu.entity.Category;
import com.odiga.owner.entity.Owner;
import com.odiga.reservation.entity.Reservation;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.review.entity.Review;
import com.odiga.table.entity.StoreTable;
import com.odiga.waiting.entity.Waiting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<StoreTable> storeTables = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<ReservationSlot> reservationSlots = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<LikeStore> likeStores = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<StoreImage> images = new ArrayList<>();

//    public void addCategory(Category category) {
//        categories.add(category);
//    }
}
