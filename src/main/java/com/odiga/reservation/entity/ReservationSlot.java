package com.odiga.reservation.entity;

import com.odiga.common.entity.BaseEntity;
import com.odiga.global.exception.CustomException;
import com.odiga.global.exception.GlobalErrorCode;
import com.odiga.reservation.enums.ReservationSlotStatus;
import com.odiga.store.entity.Store;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSlot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReservationSlotStatus reservationSlotStatus = ReservationSlotStatus.AVAILABLE;

    @Builder.Default
    @OneToMany(mappedBy = "reservationSlot")
    private List<Reservation> reservations = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public void validateStore(Long storeId) {
        if (!store.getId().equals(storeId)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST);
        }
    }

    public void changeStatus(ReservationSlotStatus status) {
        reservationSlotStatus = status;
    }
}
