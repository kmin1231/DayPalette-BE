package com.khu.cloud.diary.posts.util;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class S3UploadService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String upload(MultipartFile image) {
        try {
            // generate unique file name
            String fileName = generateUniqueFileName(image.getOriginalFilename());

            // upload file to S3
            try (InputStream inputStream = image.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(image.getContentType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));
            }

            // return file URL
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toString();

        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String extension = "";

        // extract file extension
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        return uuid + extension;
    }
}