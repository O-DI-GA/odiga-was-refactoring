package com.odiga.reservation.controller;

import com.odiga.owner.entity.Owner;
import com.odiga.reservation.api.OwnerReservationSlotApi;
import com.odiga.reservation.application.OwnerReservationSlotService;
import com.odiga.reservation.dto.ReservationSlotChangeStatusRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/owners/stores/{storeId}/reservation-slots")
public class OwnerReservationSlotController implements OwnerReservationSlotApi {

    private final OwnerReservationSlotService ownerReservationSlotService;

    @PostMapping
    public ResponseEntity<?> addReservationSlot(@AuthenticationPrincipal Owner owner,
                                                @PathVariable Long storeId,
                                                @RequestBody ReservationSlotCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ownerReservationSlotService.addAvailableReservationTime(owner, storeId, requestDto));
    }

    @GetMapping
    public ResponseEntity<?> findBetweenDate(@PathVariable Long storeId,
                                             @RequestParam LocalDate startDate,
                                             @RequestParam LocalDate endDate) {
        return ResponseEntity.ok()
            .body(ownerReservationSlotService.findByStoreIdAndBetweenReservationTime(storeId, startDate, endDate));
    }

    @DeleteMapping("{reservationSlotId}")
    public ResponseEntity<?> deleteReservationSlot(@AuthenticationPrincipal Owner owner,
                                                   @PathVariable Long storeId,
                                                   @PathVariable Long reservationSlotId) {
        ownerReservationSlotService.deleteById(owner, storeId, reservationSlotId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{reservationSlotId}")
    public ResponseEntity<?> changeStatusSlot(@AuthenticationPrincipal Owner owner,
                                              @PathVariable Long storeId,
                                              @PathVariable Long reservationSlotId,
                                              @RequestBody ReservationSlotChangeStatusRequestDto requestDto) {
        return ResponseEntity.ok()
            .body(ownerReservationSlotService.changeStatusById(owner, storeId, reservationSlotId, requestDto));
    }
}
