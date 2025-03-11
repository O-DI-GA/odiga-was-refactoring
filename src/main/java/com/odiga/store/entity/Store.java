package com.odiga.store.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.menu.entity.Category;
import com.odiga.owner.entity.Owner;
import com.odiga.reservation.entity.Reservation;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.waiting.entity.Waiting;
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
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    private String titleImageUrl;

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<ReservationSlot> reservationSlots = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
