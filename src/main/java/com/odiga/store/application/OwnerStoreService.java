package com.odiga.store.application;

import com.odiga.global.exception.CustomException;
import com.odiga.owner.entity.Owner;
import com.odiga.store.dao.StoreRepository;
import com.odiga.store.dto.StoreRegisterRequestDto;
import com.odiga.store.dto.StoreResponseDto;
import com.odiga.store.entity.Store;
import com.odiga.store.exception.StoreErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {

    private final StoreRepository storeRepository;
    private final OwnerStoreImageService ownerStoreImageService;

    @Transactional
    public StoreResponseDto registerStore(Owner owner, StoreRegisterRequestDto storeRegisterRequestDto,
                                          MultipartFile storeTitleImage, List<MultipartFile> images) {
        Store store = storeRegisterRequestDto.toEntity(owner);

        storeRepository.save(store);

        ownerStoreImageService.addStoreImages(store, storeTitleImage, images);

        return StoreResponseDto.from(store);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAllStoreByOwner(Owner owner, Pageable pageable) {

        Page<Store> stores = storeRepository.findByOwnerId(owner.getId(), pageable);

        return stores.stream().map(StoreResponseDto::from).toList();
    }

    @Transactional
    public StoreResponseDto openStore(Owner owner, Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());
        store.storeOpen();

        return StoreResponseDto.from(store);
    }

    @Transactional
    public StoreResponseDto closeStore(Owner owner, Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND_STORE));

        store.validateOwner(owner.getId());
        store.storeClose();

        return StoreResponseDto.from(store);
    }

}
