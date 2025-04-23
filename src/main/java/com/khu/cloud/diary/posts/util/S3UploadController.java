package com.khu.cloud.diary.posts.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "S3 upload", description = "image file uplaod")
public class S3UploadController {

    private final S3UploadService s3UploadService;

    public S3UploadController(S3UploadService s3UploadService) {
        this.s3UploadService = s3UploadService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "upload image to S3", description = "upload image file to S3 & return S3 URL")
    public ResponseEntity<String> uploadImage(
        @Parameter(description = "image file to upload", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "multipart/form-data"))
        @RequestPart("image") MultipartFile image,
        @RequestParam("userId") String userId
    ) {
        String uploadedImageUrl = s3UploadService.uploadMultipartFile(image, userId);
        return ResponseEntity.ok(uploadedImageUrl);
    }
}