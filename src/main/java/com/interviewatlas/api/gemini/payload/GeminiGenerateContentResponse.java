package com.interviewatlas.api.gemini.payload;

import java.util.List;

public record GeminiGenerateContentResponse(
    List<Candidate> candidates
) {

  public String firstText() {
    if (candidates == null || candidates.isEmpty()) {
      return "";
    }

    for (Candidate candidate : candidates) {
      if (candidate.content() == null || candidate.content().parts() == null) {
        continue;
      }

      for (Part part : candidate.content().parts()) {
        if (part.text() != null && !part.text().isBlank()) {
          return part.text();
        }
      }
    }

    return "";
  }

  public record Candidate(
      Content content
  ) {}

  public record Content(
      List<Part> parts
  ) {}

  public record Part(
      String text
  ) {}
}
