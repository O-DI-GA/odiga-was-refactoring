package com.odiga.store.controller;

import com.odiga.owner.entity.Owner;
import com.odiga.store.application.OwnerStoreService;
import com.odiga.store.dto.StoreRegisterRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/owners/stores")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;


    @PostMapping
    public ResponseEntity<?> registerOwnerStore(@AuthenticationPrincipal Owner owner,
                                                @RequestPart StoreRegisterRequestDto storeRegisterRequestDto,
                                                @RequestPart MultipartFile storeTitleImage,
                                                @RequestPart List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ownerStoreService.registerStore(owner, storeRegisterRequestDto, storeTitleImage, images));

    }

    @GetMapping
    public ResponseEntity<?> findAllOwnerStore(@AuthenticationPrincipal Owner owner) {
        return ResponseEntity.ok(ownerStoreService.findAllStoreByOwner(owner));
    }

}
