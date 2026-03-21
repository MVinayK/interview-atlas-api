package com.interviewatlas.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewatlas.api.dto.ApproachReviewRequest;
import com.interviewatlas.api.dto.ApproachReviewResponse;
import com.interviewatlas.api.dto.ClarificationRequest;
import com.interviewatlas.api.dto.ClarificationResponse;
import com.interviewatlas.api.dto.CodeEvaluationRequest;
import com.interviewatlas.api.dto.CodeEvaluationResponse;
import com.interviewatlas.api.dto.HintRequest;
import com.interviewatlas.api.dto.HintResponse;
import com.interviewatlas.api.dto.ProblemDraftRequest;
import com.interviewatlas.api.dto.ProblemDraftResponse;
import com.interviewatlas.api.provider.AiProviderRegistry;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AiInterviewService {

  private final AiProviderRegistry aiProviderRegistry;
  private final ObjectMapper objectMapper;

  public AiInterviewService(AiProviderRegistry aiProviderRegistry, ObjectMapper objectMapper) {
    this.aiProviderRegistry = aiProviderRegistry;
    this.objectMapper = objectMapper;
  }

  public ProblemDraftResponse generateProblemDraft(ProblemDraftRequest request) {
    return requestStructuredResponse(
        Prompts.problemDraftPrompt(request),
        ProblemDraftResponse.class,
        Schemas.problemDraftSchema()
    );
  }

  public ApproachReviewResponse reviewApproach(ApproachReviewRequest request) {
    return requestStructuredResponse(
        Prompts.approachReviewPrompt(request),
        ApproachReviewResponse.class,
        Schemas.approachReviewSchema()
    );
  }

  public ClarificationResponse answerClarification(ClarificationRequest request) {
    return requestStructuredResponse(
        Prompts.clarificationPrompt(request),
        ClarificationResponse.class,
        Schemas.clarificationSchema()
    );
  }

  public HintResponse generateHint(HintRequest request) {
    return requestStructuredResponse(
        Prompts.hintPrompt(request),
        HintResponse.class,
        Schemas.hintSchema()
    );
  }

  public CodeEvaluationResponse evaluateCode(CodeEvaluationRequest request) {
    return requestStructuredResponse(
        Prompts.codeEvaluationPrompt(request),
        CodeEvaluationResponse.class,
        Schemas.codeEvaluationSchema()
    );
  }

  private <T> T requestStructuredResponse(
      String prompt,
      Class<T> responseType,
      Map<String, Object> schema
  ) {
    String content = aiProviderRegistry.activeProvider()
        .requestStructuredResponse(responseType.getSimpleName(), prompt, schema);

    try {
      return objectMapper.readValue(content, responseType);
    } catch (JsonProcessingException exception) {
      throw new IllegalStateException("Failed to parse AI response: " + content, exception);
    }
  }
}
