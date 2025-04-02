package com.odiga.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.odiga.common.exception.S3ErrorCode;
import com.odiga.global.exception.CustomException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadS3(String dirName, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String extension = getImageExtension(multipartFile);
            String fileName = dirName + UUID.randomUUID() + extension;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/" + extension);

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata));
            return amazonS3.getUrl(bucketName, fileName).toString();

        } catch (IOException e) {
            throw new CustomException(S3ErrorCode.FILE_COVERT);
        }
    }

    private String getImageExtension(MultipartFile multipartFile) {
        String imageName = multipartFile.getOriginalFilename();
        if (imageName == null || !imageName.contains(".")) {
            throw new CustomException(S3ErrorCode.UNSUPPORTED_FILE);
        }
        return imageName.substring(imageName.lastIndexOf("."));
    }

    public void deleteImage(String key) {
        DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, key);
        amazonS3.deleteObject(deleteRequest);
    }

}
