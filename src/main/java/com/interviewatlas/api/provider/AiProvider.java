package com.interviewatlas.api.provider;

import java.util.Map;

public interface AiProvider {

  boolean supports(String providerName);

  String requestStructuredResponse(String schemaName, String prompt, Map<String, Object> schema);
}
