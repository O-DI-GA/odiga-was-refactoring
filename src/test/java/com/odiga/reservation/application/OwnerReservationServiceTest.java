package com.odiga.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.owner.entity.Owner;
import com.odiga.reservation.dao.ReservationRepository;
import com.odiga.reservation.dao.ReservationSlotRepository;
import com.odiga.reservation.dto.ReservationSlotChangeStatusRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto.DaySchedule;
import com.odiga.reservation.dto.ReservationSlotInfoDto;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.reservation.entity.ReservationSlotStatus;
import com.odiga.reservation.entity.WeekDay;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerReservationServiceTest {

    @InjectMocks
    OwnerReservationSlotService ownerReservationSlotService;

    @Mock
    StoreRepository storeRepository;

    @Mock
    ReservationSlotRepository reservationSlotRepository;

    @Mock
    ReservationRepository reservationRepository;

    Long storeId = 1L;

    Owner owner = Owner.builder()
        .id(1L)
        .build();


    Store store = Store.builder()
        .id(storeId)
        .owner(owner)
        .build();


    @Test
    void addReservationSlotTest() {
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        DaySchedule daySchedule =
            new DaySchedule(WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(9, 30), 30);

        ReservationSlotCreateRequestDto requestDto = new ReservationSlotCreateRequestDto(2025, 4, List.of(daySchedule));
        List<ReservationSlotInfoDto> result = ownerReservationSlotService.addAvailableReservationTime(owner, storeId, requestDto);

        assertThat(result.size()).isEqualTo(8);
    }

    @Test
    void deleteReservationSlotTest() {
        Long reservationSlotId = 1L;
        ReservationSlot reservationSlot = ReservationSlot.builder()
            .store(store)
            .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(reservationSlotRepository.findById(reservationSlotId)).thenReturn(Optional.of(reservationSlot));

        ownerReservationSlotService.deleteById(owner, storeId, reservationSlotId);

        verify(reservationSlotRepository, times(1)).delete(reservationSlot);
    }

    @Test
    void changeSlotStatusTest() {
        Long reservationSlotId = 1L;
        ReservationSlot reservationSlot = ReservationSlot.builder()
            .store(store)
            .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(reservationSlotRepository.findById(reservationSlotId)).thenReturn(Optional.of(reservationSlot));

        ReservationSlotStatus reservationSlotStatus = ReservationSlotStatus.AVAILABLE;

        ReservationSlotChangeStatusRequestDto requestDto = new ReservationSlotChangeStatusRequestDto(reservationSlotStatus);

        ReservationSlotInfoDto reservationSlotInfoDto = ownerReservationSlotService.changeStatusById(owner, storeId, reservationSlotId, requestDto);

        assertThat(reservationSlotInfoDto.status()).isEqualTo(reservationSlotStatus);
    }
}
