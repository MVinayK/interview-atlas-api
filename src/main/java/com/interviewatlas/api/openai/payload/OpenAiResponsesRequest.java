package com.interviewatlas.api.openai.payload;

import java.util.List;
import java.util.Map;

public record OpenAiResponsesRequest(
    String model,
    List<Map<String, Object>> input,
    Map<String, Object> text
) {

  public OpenAiResponsesRequest withModel(String nextModel) {
    return new OpenAiResponsesRequest(nextModel, input, text);
  }
}
