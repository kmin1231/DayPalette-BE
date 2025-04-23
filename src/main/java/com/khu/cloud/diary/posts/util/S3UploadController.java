package com.khu.cloud.diary.posts.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "S3 Upload", description = "image file uplaod API")
public class S3UploadController {

    private final S3UploadService s3UploadService;

    public S3UploadController(S3UploadService s3UploadService) {
        this.s3UploadService = s3UploadService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload image to S3", description = "Uploads an image file to S3 and returns its URL.")
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Image file to upload", content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "multipart/form-data"))
            @RequestPart("image") MultipartFile image) {

        String uploadedImageUrl = s3UploadService.upload(image);
        return ResponseEntity.ok(uploadedImageUrl);
    }
}