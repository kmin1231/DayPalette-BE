package com.khu.cloud.diary.posts.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import com.khu.cloud.diary.member.util.JwtUtil;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "S3 upload", description = "image file uplaod")
public class S3UploadController {

    private final S3UploadService s3UploadService;
    private final JwtUtil jwtUtil;

    public S3UploadController(S3UploadService s3UploadService, JwtUtil jwtUtil) {
        this.s3UploadService = s3UploadService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "upload image to S3", description = "upload image file to S3 & return S3 URL")
    public ResponseEntity<String> uploadImage(
        @Parameter(description = "image file to upload", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "multipart/form-data"))
        @RequestPart("image") MultipartFile image,
        HttpServletRequest request
    ) {
        // Authorization header에서 token 추출
        String authHeader = request.getHeader("Authorization");
        String token = authHeader != null && authHeader.startsWith("Bearer ")
            ? authHeader.substring(7)
            : null;

        String email = jwtUtil.extractEmail(token);

        String uploadedImageUrl = s3UploadService.uploadMultipartFile(image, email);
        return ResponseEntity.ok(uploadedImageUrl);
    }
}