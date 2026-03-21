package com.interviewatlas.api.openai;

import com.interviewatlas.api.config.AiProperties;
import com.interviewatlas.api.openai.payload.OpenAiResponsesRequest;
import com.interviewatlas.api.openai.payload.OpenAiResponsesResponse;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class OpenAiResponsesClient {

  private final RestClient restClient;
  private final AiProperties aiProperties;

  public OpenAiResponsesClient(RestClient openAiRestClient, AiProperties aiProperties) {
    this.restClient = openAiRestClient;
    this.aiProperties = aiProperties;
  }

  public OpenAiResponsesResponse createResponse(OpenAiResponsesRequest request) {
    return restClient.post()
        .uri("/responses")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.withModel(aiProperties.model()))
        .retrieve()
        .body(OpenAiResponsesResponse.class);
  }
}
