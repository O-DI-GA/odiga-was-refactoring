package com.odiga.table.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record StoreTableCreateRequestDto(

    @NotBlank(message = "좌석수는 필수입니다.")
    @Min(value = 1, message = "좌석수는 음수일 수 없습니다.")
    int maxSeat,

    @NotBlank(message = "테이블 번호는 필수입니다.")
    @Min(value = 1, message = "테이블 번호는 음수일 수 없습니다.")
    int tableNumber
) {

}
