package com.odiga.store.application;

import com.odiga.global.s3.application.S3ImageService;
import com.odiga.store.dao.StoreImageRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.entity.StoreImage;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OwnerStoreImageService {

    private final static String STORE_IMAGE_DIR_NAME = "store";

    private final StoreImageRepository storeImageRepository;
    private final S3ImageService s3ImageService;

    @Transactional
    public void addStoreImages(Store store, MultipartFile titleStoreImage, List<MultipartFile> storeImages) {

        String titleStoreImageUrl = s3ImageService.uploadS3(STORE_IMAGE_DIR_NAME, titleStoreImage);
        List<StoreImage> storeImageUrls = new ArrayList<>();

        for (MultipartFile multipartFile : storeImages) {
            String imageUrl = s3ImageService.uploadS3(STORE_IMAGE_DIR_NAME, multipartFile);

            StoreImage storeImage = StoreImage.builder()
                .imageUrl(imageUrl)
                .store(store)
                .build();

            storeImageUrls.add(storeImage);
            storeImageRepository.save(storeImage);
        }

        store.createStoreImages(titleStoreImageUrl, storeImageUrls);
    }

}
