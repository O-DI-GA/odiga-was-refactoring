package com.odiga.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.global.s3.application.S3ImageService;
import com.odiga.store.dao.StoreImageRepository;
import com.odiga.store.entity.Store;
import com.odiga.store.entity.StoreImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class OwnerStoreImageServiceTest {

    static final String STORE_IMAGE_DIR_NAME = "store";

    @InjectMocks
    OwnerStoreImageService ownerStoreImageService;

    @Mock
    StoreImageRepository storeImageRepository;

    @Mock
    S3ImageService s3ImageService;

    Store store;

    @BeforeEach
    void init() {
        store = Store.builder()
            .name("store")
            .build();
    }

    @Test
    void addStoreImageOnlyTitleImageTest() {

        String titleImageUrl = "example.com/title_image.jpg";

        MockMultipartFile multipartFile = new MockMultipartFile("titleImage", new byte[0]);

        when(s3ImageService.uploadS3(STORE_IMAGE_DIR_NAME, multipartFile)).thenReturn(titleImageUrl);

        ownerStoreImageService.addStoreImages(store, multipartFile, new ArrayList<>());

        assertThat(store.getTitleImageUrl()).isEqualTo(titleImageUrl);
        assertThat(store.getImages().size()).isEqualTo(0);
    }

    @Test
    void addStoreImageTest() {

        String titleImageUrl = "example.com/title_image.jpg";

        MockMultipartFile multipartFile = new MockMultipartFile("titleImage", new byte[0]);

        when(s3ImageService.uploadS3(STORE_IMAGE_DIR_NAME, multipartFile)).thenReturn(titleImageUrl);

        List<MultipartFile> images = new ArrayList<>
            (List.of(new MockMultipartFile("image1", new byte[0]),
                new MockMultipartFile("image2", new byte[0]),
                new MockMultipartFile("image3", new byte[0])));

        for (MultipartFile image : images) {
            String imageUrl = "example.com/" + image.getName() + ".jpg";
            when(s3ImageService.uploadS3(STORE_IMAGE_DIR_NAME, image)).thenReturn(imageUrl);
        }

        ownerStoreImageService.addStoreImages(store, multipartFile, images);

        assertThat(store.getTitleImageUrl()).isEqualTo(titleImageUrl);
        assertThat(store.getImages().size()).isEqualTo(images.size());

        verify(storeImageRepository, times(images.size())).save(any(StoreImage.class));
    }
}