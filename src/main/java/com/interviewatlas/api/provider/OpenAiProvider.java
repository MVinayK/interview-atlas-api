package com.interviewatlas.api.provider;

import com.interviewatlas.api.config.AiProperties;
import com.interviewatlas.api.openai.payload.OpenAiResponsesRequest;
import com.interviewatlas.api.openai.payload.OpenAiResponsesResponse;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenAiProvider implements AiProvider {

  private final RestClient restClient;
  private final AiProperties aiProperties;

  public OpenAiProvider(RestClient.Builder restClientBuilder, AiProperties aiProperties) {
    this.aiProperties = aiProperties;
    this.restClient = restClientBuilder
        .baseUrl(aiProperties.resolvedBaseUrl())
        .defaultHeader("Authorization", "Bearer " + aiProperties.apiKey())
        .defaultHeader("Content-Type", "application/json")
        .build();
  }

  @Override
  public boolean supports(String providerName) {
    return "openai".equalsIgnoreCase(providerName);
  }

  @Override
  public String requestStructuredResponse(
      String schemaName,
      String prompt,
      Map<String, Object> schema
  ) {
    OpenAiResponsesRequest request = new OpenAiResponsesRequest(
        aiProperties.model(),
        List.of(Map.of(
            "role", "user",
            "content", List.of(Map.of(
                "type", "input_text",
                "text", prompt
            ))
        )),
        Map.of(
            "format", Map.of(
                "type", "json_schema",
                "name", schemaName,
                "strict", true,
                "schema", schema
            )
        )
    );

    OpenAiResponsesResponse response = AiRetrySupport.execute(() -> restClient.post()
        .uri("/responses")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(OpenAiResponsesResponse.class));

    return response == null ? "" : response.firstText();
  }
}
