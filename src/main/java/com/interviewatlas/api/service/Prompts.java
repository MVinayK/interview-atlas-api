package com.interviewatlas.api.service;

import com.interviewatlas.api.dto.ApproachReviewRequest;
import com.interviewatlas.api.dto.ClarificationRequest;
import com.interviewatlas.api.dto.CodeEvaluationRequest;
import com.interviewatlas.api.dto.HintRequest;
import com.interviewatlas.api.dto.ProblemDraftRequest;

final class Prompts {

  private Prompts() {}

  static String problemDraftPrompt(ProblemDraftRequest request) {
    return """
        You are a strict software engineering interviewer.
        Generate a concise interview-ready problem package.
        Do not include a solution.

        Company: %s
        Title: %s
        Difficulty: %s
        Extra context: %s
        """.formatted(
        request.company(),
        request.title(),
        blankSafe(request.difficulty()),
        blankSafe(request.sourcePrompt())
    );
  }

  static String approachReviewPrompt(ApproachReviewRequest request) {
    return """
        You are a strict interviewer, not a tutor.
        Review the candidate's approach briefly.
        Do not give the solution.

        Problem:
        %s

        Candidate approach:
        %s
        """.formatted(request.problemStatement(), request.approach());
  }

  static String clarificationPrompt(ClarificationRequest request) {
    return """
        You are a strict interviewer.
        Answer in one short clarification only.
        Do not expand beyond what the candidate asked.
        If clarification count is already high, keep it terse.

        Clarification count so far: %d

        Problem:
        %s

        Candidate question:
        %s
        """.formatted(
        request.clarificationCount(),
        request.problemStatement(),
        request.question()
    );
  }

  static String hintPrompt(HintRequest request) {
    return """
        You are a strict interviewer.
        Give exactly one hint at the requested level.
        Do not give the full solution.
        Hint level 1 must be directional.
        Hint level 2 may be stronger but still not a solution.

        Problem:
        %s

        Candidate approach:
        %s

        Requested hint level: %d
        """.formatted(
        request.problemStatement(),
        request.approach(),
        request.hintLevel()
    );
  }

  static String codeEvaluationPrompt(CodeEvaluationRequest request) {
    return """
        You are a strict coding interviewer.
        Evaluate the candidate code without providing a full solution.
        Focus on likely correctness issues, edge cases, complexity, and code quality.
        Keep feedback concise and interview-style.

        Problem:
        %s

        Candidate approach:
        %s

        Language:
        %s

        Code:
        %s
        """.formatted(
        request.problemStatement(),
        request.approach(),
        request.language(),
        request.code()
    );
  }

  private static String blankSafe(String value) {
    return value == null || value.isBlank() ? "n/a" : value;
  }
}
