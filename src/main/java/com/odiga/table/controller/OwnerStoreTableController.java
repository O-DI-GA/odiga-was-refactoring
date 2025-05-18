package com.odiga.table.controller;


import com.odiga.common.dto.ApiResponse;
import com.odiga.owner.entity.Owner;
import com.odiga.table.api.OwnerStoreTableApi;
import com.odiga.table.application.OwnerStoreTableService;
import com.odiga.table.dto.StoreTableCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/owners/stores/{storeId}/store-tables")
@RequiredArgsConstructor
public class OwnerStoreTableController implements OwnerStoreTableApi {

    private final OwnerStoreTableService ownerStoreTableService;

    @PostMapping
    public ResponseEntity<?> addStoreTable(@AuthenticationPrincipal Owner owner,
                                           @PathVariable Long storeId,
                                           @Valid @RequestBody StoreTableCreateRequestDto storeTableCreateRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok(ownerStoreTableService.addStoreTable(owner, storeId, storeTableCreateRequestDto)));
    }

    @GetMapping
    public ResponseEntity<?> findByStoreId(@AuthenticationPrincipal Owner owner, @PathVariable Long storeId) {
        return ResponseEntity.ok(ApiResponse.ok(ownerStoreTableService.findStoreTableByStoreId(owner, storeId)));
    }
}
