package com.interviewatlas.api.provider;

import com.interviewatlas.api.config.AiProperties;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AiProviderRegistry {

  private final AiProvider activeProvider;

  public AiProviderRegistry(List<AiProvider> providers, AiProperties aiProperties) {
    this.activeProvider = providers.stream()
        .filter(provider -> provider.supports(aiProperties.provider()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "No AI provider configured for: " + aiProperties.provider()
        ));
  }

  public AiProvider activeProvider() {
    return activeProvider;
  }
}
