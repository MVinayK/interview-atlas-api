package com.interviewatlas.api.provider;

import com.interviewatlas.api.config.AiProperties;
import com.interviewatlas.api.gemini.payload.GeminiGenerateContentRequest;
import com.interviewatlas.api.gemini.payload.GeminiGenerateContentResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GeminiProvider implements AiProvider {

  private final RestClient restClient;
  private final AiProperties aiProperties;

  public GeminiProvider(RestClient.Builder restClientBuilder, AiProperties aiProperties) {
    this.aiProperties = aiProperties;
    this.restClient = restClientBuilder
        .baseUrl(aiProperties.resolvedBaseUrl())
        .defaultHeader("x-goog-api-key", aiProperties.apiKey())
        .defaultHeader("Content-Type", "application/json")
        .build();
  }

  @Override
  public boolean supports(String providerName) {
    return "gemini".equalsIgnoreCase(providerName);
  }

  @Override
  public String requestStructuredResponse(
      String schemaName,
      String prompt,
      Map<String, Object> schema
  ) {
    GeminiGenerateContentRequest request = new GeminiGenerateContentRequest(
        List.of(new GeminiGenerateContentRequest.Content(
            List.of(new GeminiGenerateContentRequest.Part(prompt))
        )),
        new GeminiGenerateContentRequest.GenerationConfig(
            "application/json",
            sanitizeSchema(schema)
        )
    );

    GeminiGenerateContentResponse response = AiRetrySupport.execute(() -> restClient.post()
        .uri("/models/{model}:generateContent", aiProperties.model())
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(GeminiGenerateContentResponse.class));

    return response == null ? "" : response.firstText();
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> sanitizeSchema(Map<String, Object> schema) {
    return (Map<String, Object>) sanitizeValue(schema);
  }

  @SuppressWarnings("unchecked")
  private Object sanitizeValue(Object value) {
    if (value instanceof Map<?, ?> mapValue) {
      Map<String, Object> sanitized = new LinkedHashMap<>();

      for (Map.Entry<?, ?> entry : mapValue.entrySet()) {
        if ("additionalProperties".equals(entry.getKey())) {
          continue;
        }

        sanitized.put(String.valueOf(entry.getKey()), sanitizeValue(entry.getValue()));
      }

      return sanitized;
    }

    if (value instanceof List<?> listValue) {
      List<Object> sanitized = new ArrayList<>(listValue.size());

      for (Object item : listValue) {
        sanitized.add(sanitizeValue(item));
      }

      return sanitized;
    }

    return value;
  }
}
