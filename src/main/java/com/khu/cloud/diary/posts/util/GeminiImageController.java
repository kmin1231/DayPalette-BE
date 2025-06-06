package com.khu.cloud.diary.posts.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.util.*;

import com.khu.cloud.diary.member.util.JwtUtil;
import com.khu.cloud.diary.posts.dto.GenerateImageRequest;
import com.khu.cloud.diary.posts.dto.GenerateImageResponse;
import com.khu.cloud.diary.posts.util.S3UploadService;
import com.khu.cloud.diary.posts.util.TranslationService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class GeminiImageController {

    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private static final String PROMPT_SUFFIX =
        "Based on the text, please create a warm, emotional, dreamy and soft diary-style illustration that reflects the mood, scenery, and feelings of the moment. Do not include any text, words, sentences, or letters in the image.";

    private final S3UploadService s3UploadService;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final TranslationService translationService;

    @PostMapping("/generate-image")
    public ResponseEntity<?> generateImage(@RequestBody GenerateImageRequest requestDto) throws Exception {
        if (!StringUtils.hasText(requestDto.getDiaryText())) {
            return ResponseEntity.badRequest().body("Diary text is required");
        }

        // text translation
        String englishText = translationService.translateToEnglish(requestDto.getDiaryText());

        String url = geminiApiUrl + "?key=" + apiKey;

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", englishText + PROMPT_SUFFIX)
                ))
            ),
            "generationConfig", Map.of(
                "responseModalities", List.of("TEXT", "IMAGE")
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();

            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode partsNode = rootNode.path("candidates").get(0).path("content").path("parts");

            for (JsonNode part : partsNode) {
                JsonNode dataNode = part.path("inlineData").path("data");
                if (!dataNode.isMissingNode()) {
                    String base64Image = dataNode.asText();

                    String email = extractEmailFromJwt();

                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                    String fileName = FileNameGenerator.generateFileName(email);

                    // S3 bucket에 이미지 업로드
                    String imageUrl = s3UploadService.uploadImageBytes(imageBytes, email, fileName, "image/png");

                    // S3 URL
                    return ResponseEntity.ok(new GenerateImageResponse(imageUrl));

                }
            }

            return ResponseEntity.internalServerError().body("Image data not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    private String extractEmailFromJwt() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.extractEmail(token);
    }
}