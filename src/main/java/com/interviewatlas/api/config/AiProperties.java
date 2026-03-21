package com.interviewatlas.api.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "interview-atlas.ai")
public record AiProperties(
    @NotBlank String provider,
    @NotBlank String apiKey,
    String baseUrl,
    @NotBlank String model
) {

  public String resolvedBaseUrl() {
    if (baseUrl != null && !baseUrl.isBlank()) {
      return baseUrl;
    }

    return switch (provider.toLowerCase()) {
      case "gemini" -> "https://generativelanguage.googleapis.com/v1beta";
      case "openai" -> "https://api.openai.com/v1";
      default -> throw new IllegalStateException("Unsupported AI provider: " + provider);
    };
  }

  public boolean isOpenAi() {
    return "openai".equalsIgnoreCase(provider);
  }

  public boolean isGemini() {
    return "gemini".equalsIgnoreCase(provider);
  }
}
