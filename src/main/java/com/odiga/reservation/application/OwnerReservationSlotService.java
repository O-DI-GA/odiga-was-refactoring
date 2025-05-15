package com.odiga.reservation.application;

import com.odiga.global.exception.CustomException;
import com.odiga.owner.entity.Owner;
import com.odiga.reservation.dao.ReservationSlotRepository;
import com.odiga.reservation.dto.ReservationSlotChangeStatusRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto;
import com.odiga.reservation.dto.ReservationSlotCreateRequestDto.DaySchedule;
import com.odiga.reservation.dto.ReservationSlotInfoDto;
import com.odiga.reservation.entity.ReservationSlot;
import com.odiga.reservation.exception.ReservationSlotErrorCode;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.exception.StoreErrorCode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerReservationSlotService {

    private final StoreRepository storeRepository;
    private final ReservationSlotRepository reservationSlotRepository;

    // TODO : 해당 메소드 리팩토링 필요함
    @Transactional
    public List<ReservationSlotInfoDto> addAvailableReservationTime(Owner owner,
                                                                    Long storeId,
                                                                    ReservationSlotCreateRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        LocalDate startDate = LocalDate.of(requestDto.year(), requestDto.month(), 1);
        LocalDate endDate = YearMonth.of(requestDto.year(), requestDto.month()).atEndOfMonth();

        List<ReservationSlot> reservationSlots = reservationSlotRepository.findByStoreIdAndBetweenReservationTime(storeId, startDate, endDate);

        Set<LocalDateTime> dateTimes = reservationSlots.stream().map(ReservationSlot::getReservationTime)
            .collect(Collectors.toSet());

        Set<LocalDateTime> times = new HashSet<>();

        for (DaySchedule daySchedule : requestDto.daySchedules()) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(daySchedule.dayOfWeek().name());
            LocalTime startTime = daySchedule.startTime();
            LocalTime endTime = daySchedule.endTime();
            int interval = daySchedule.interval();

            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                if (currentDate.getDayOfWeek() == dayOfWeek) {
                    LocalTime currentTime = startTime;
                    while (!currentTime.isAfter(endTime)) {
                        LocalDateTime localDateTime = LocalDateTime.of(currentDate, currentTime);
                        if (!dateTimes.contains(localDateTime)) {
                            times.add(localDateTime);
                        }
                        currentTime = currentTime.plusMinutes(interval);
                    }
                }
                currentDate = currentDate.plusDays(1);
            }
        }

        List<ReservationSlot> saveList = new ArrayList<>();
        for (LocalDateTime dateTime : times) {
            ReservationSlot reservationSlot = ReservationSlot.builder()
                .reservationTime(dateTime)
                .build();

            saveList.add(reservationSlot);
            store.addReservationSlot(reservationSlot);
        }

        reservationSlotRepository.saveAllBatch(saveList);

        List<ReservationSlot> result = reservationSlotRepository.findByStoreIdAndBetweenReservationTime(storeId, startDate, endDate);
        return result.stream().map(ReservationSlotInfoDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationSlotInfoDto> findByStoreIdAndBetweenReservationTime(Owner owner,
                                                                               Long storeId,
                                                                               LocalDate startDate,
                                                                               LocalDate endDate) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        List<ReservationSlot> reservationSlots = reservationSlotRepository.findByStoreIdAndBetweenReservationTime(storeId, startDate, endDate);

        return reservationSlots.stream().map(ReservationSlotInfoDto::from).toList();
    }

    @Transactional
    public ReservationSlotInfoDto changeStatusById(Owner owner, Long storeId, Long reservationSlotId,
                                                   ReservationSlotChangeStatusRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        ReservationSlot reservationSlot = reservationSlotRepository
            .findById(reservationSlotId).orElseThrow(() -> new CustomException(ReservationSlotErrorCode.NOT_FOUND));

        reservationSlot.changeStatus(requestDto.status());

        return ReservationSlotInfoDto.from(reservationSlot);
    }

    @Transactional
    public void deleteById(Owner owner, Long storeId, Long reservationSlotId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());

        ReservationSlot reservationSlot = reservationSlotRepository
            .findById(reservationSlotId).orElseThrow(() -> new CustomException(ReservationSlotErrorCode.NOT_FOUND));

        reservationSlot.validateStore(store.getId());

        reservationSlotRepository.delete(reservationSlot);
    }
}
