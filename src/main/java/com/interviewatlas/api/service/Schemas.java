package com.interviewatlas.api.service;

import java.util.List;
import java.util.Map;

final class Schemas {

  private Schemas() {}

  static Map<String, Object> problemDraftSchema() {
    return Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "statement", stringField(),
            "constraints", stringField(),
            "inputFormat", stringField(),
            "outputFormat", stringField(),
            "examples", Map.of(
                "type", "array",
                "items", Map.of(
                    "type", "object",
                    "additionalProperties", false,
                    "properties", Map.of(
                        "input", stringField(),
                        "output", stringField(),
                        "explanation", stringField()
                    ),
                    "required", List.of("input", "output", "explanation")
                )
            ),
            "edgeCaseHint", stringField()
        ),
        "required", List.of("statement", "constraints", "inputFormat", "outputFormat", "examples", "edgeCaseHint")
    );
  }

  static Map<String, Object> approachReviewSchema() {
    return Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "verdict", stringField(),
            "feedback", stringField()
        ),
        "required", List.of("verdict", "feedback")
    );
  }

  static Map<String, Object> clarificationSchema() {
    return Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "answer", stringField(),
            "clarificationLimitReached", Map.of("type", "boolean")
        ),
        "required", List.of("answer", "clarificationLimitReached")
    );
  }

  static Map<String, Object> hintSchema() {
    return Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "hintLevel", Map.of("type", "integer"),
            "hint", stringField()
        ),
        "required", List.of("hintLevel", "hint")
    );
  }

  static Map<String, Object> codeEvaluationSchema() {
    Map<String, Object> scoreSchema = Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "score", Map.of("type", "integer"),
            "summary", stringField()
        ),
        "required", List.of("score", "summary")
    );

    return Map.of(
        "type", "object",
        "additionalProperties", false,
        "properties", Map.of(
            "correctness", scoreSchema,
            "edgeCases", scoreSchema,
            "efficiency", scoreSchema,
            "codeQuality", scoreSchema,
            "risks", stringArray(),
            "complexityNotes", stringArray(),
            "nextStep", stringField()
        ),
        "required", List.of(
            "correctness",
            "edgeCases",
            "efficiency",
            "codeQuality",
            "risks",
            "complexityNotes",
            "nextStep"
        )
    );
  }

  private static Map<String, Object> stringField() {
    return Map.of("type", "string");
  }

  private static Map<String, Object> stringArray() {
    return Map.of(
        "type", "array",
        "items", stringField()
    );
  }
}
