package com.interviewatlas.api.openai.payload;

import java.util.List;

public record OpenAiResponsesResponse(
    List<OutputItem> output
) {

  public String firstText() {
    if (output == null || output.isEmpty()) {
      return "";
    }

    for (OutputItem outputItem : output) {
      if (outputItem.content() == null) {
        continue;
      }

      for (ContentItem contentItem : outputItem.content()) {
        if (contentItem.text() != null && !contentItem.text().isBlank()) {
          return contentItem.text();
        }
      }
    }

    return "";
  }

  public record OutputItem(
      String type,
      List<ContentItem> content
  ) {}

  public record ContentItem(
      String type,
      String text
  ) {}
}
