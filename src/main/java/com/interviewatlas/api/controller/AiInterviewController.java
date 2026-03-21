package com.interviewatlas.api.controller;

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
import com.interviewatlas.api.service.AiInterviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiInterviewController {

  private final AiInterviewService aiInterviewService;

  public AiInterviewController(AiInterviewService aiInterviewService) {
    this.aiInterviewService = aiInterviewService;
  }

  @GetMapping("/health")
  public String health() {
    return "ok";
  }

  @PostMapping("/problem")
  public ProblemDraftResponse generateProblem(@Valid @RequestBody ProblemDraftRequest request) {
    return aiInterviewService.generateProblemDraft(request);
  }

  @PostMapping("/approach-review")
  public ApproachReviewResponse reviewApproach(@Valid @RequestBody ApproachReviewRequest request) {
    return aiInterviewService.reviewApproach(request);
  }

  @PostMapping("/clarify")
  public ClarificationResponse clarify(@Valid @RequestBody ClarificationRequest request) {
    return aiInterviewService.answerClarification(request);
  }

  @PostMapping("/hint")
  public HintResponse hint(@Valid @RequestBody HintRequest request) {
    return aiInterviewService.generateHint(request);
  }

  @PostMapping("/evaluate")
  public CodeEvaluationResponse evaluate(@Valid @RequestBody CodeEvaluationRequest request) {
    return aiInterviewService.evaluateCode(request);
  }
}
