package com.odiga.store.controller;

import com.odiga.common.dto.ApiResponse;
import com.odiga.owner.entity.Owner;
import com.odiga.store.api.OwnerStoreApi;
import com.odiga.store.application.OwnerStoreService;
import com.odiga.store.dto.StoreRegisterRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/owners/stores")
@RequiredArgsConstructor
public class OwnerStoreController implements OwnerStoreApi {

    private final OwnerStoreService ownerStoreService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerOwnerStore(@AuthenticationPrincipal Owner owner,
                                                @Valid @RequestPart StoreRegisterRequestDto storeRegisterRequestDto,
                                                @Valid @RequestPart MultipartFile storeTitleImage,
                                                @RequestPart List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok(ownerStoreService.registerStore(owner, storeRegisterRequestDto, storeTitleImage, images)));

    }

    @GetMapping
    public ResponseEntity<?> findAllOwnerStore(@AuthenticationPrincipal Owner owner, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(ownerStoreService.findAllStoreByOwner(owner, pageable)));
    }

    @PutMapping("{storeId}/open")
    public ResponseEntity<?> changeStoreStatusToOpen(@AuthenticationPrincipal Owner owner,
                                                     @PathVariable Long storeId) {
        return ResponseEntity.ok(ApiResponse.ok(ownerStoreService.openStore(owner, storeId)));
    }

    @PutMapping("{storeId}/close")
    public ResponseEntity<?> changeStoreStatusToClose(@AuthenticationPrincipal Owner owner,
                                                      @PathVariable Long storeId) {
        return ResponseEntity.ok(ApiResponse.ok(ownerStoreService.closeStore(owner, storeId)));
    }

}
