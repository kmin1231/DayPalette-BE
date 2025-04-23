package com.khu.cloud.diary.posts.util;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class S3UploadService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // multipart file upload
    public String uploadMultipartFile(MultipartFile image, String userId) {
        try {
            // generate unique file name
            String fileName = FileNameGenerator.generateFileName(userId);
            
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
            return getFileUrl(fileName);
    
        } catch (IOException | S3Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    // image binary data upload
    public String uploadImageBytes(byte[] imageBytes, String userId, String originalFileName, String contentType) {
        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            String fileName = FileNameGenerator.generateFileName(userId);
    
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType != null ? contentType : "image/png")
                    .build();
    
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, imageBytes.length));
            return getFileUrl(fileName);
    
        } catch (IOException | S3Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    private String getFileUrl(String fileName) {
        return s3Client.utilities()
                .getUrl(builder -> builder.bucket(bucketName).key(fileName))
                .toString();
    }
}