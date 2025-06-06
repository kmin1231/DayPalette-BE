// posts/util/TranslationService.java

package com.khu.cloud.diary.posts.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String TRANSLATION_MODEL_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String translateToEnglish(String text) throws Exception {
        if (text == null || text.isBlank()) {
            return text;
        }

        if (!text.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return text;
        }

        String prompt = "Translate the following diary text into natural, warm English that preserves the emotional tone:\n" + text;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String urlWithKey = TRANSLATION_MODEL_URL + "?key=" + apiKey;

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithKey,
                HttpMethod.POST,
                entity,
                String.class
        );

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode candidates = root.path("candidates");
        if (candidates.isArray() && candidates.size() > 0) {
            JsonNode textNode = candidates.get(0).path("content").path("parts").get(0).path("text");
            if (!textNode.isMissingNode()) {
                String fullText = textNode.asText().trim();

                String[] lines = fullText.split("\n");
                for (int i = lines.length - 1; i >= 0; i--) {
                    String line = lines[i].trim();
                    if (!line.isEmpty() && !line.startsWith("The translation")) {
                        return line.replaceAll("^\\*+|\\*+$", "");
                    }
                }

                return fullText;
            }
        }

        return text;
    }
}