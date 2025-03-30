package com.odiga.owner.dto;

import com.odiga.owner.entity.Owner;
import lombok.Builder;

@Builder
public record OwnerInfoResponseDto(Long id, String email, String name) {

    public static OwnerInfoResponseDto from(Owner owner) {
        return OwnerInfoResponseDto.builder()
            .id(owner.getId())
            .email(owner.getEmail())
            .name(owner.getName())
            .build();
    }
}
