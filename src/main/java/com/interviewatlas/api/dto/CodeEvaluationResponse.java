package com.interviewatlas.api.dto;

import java.util.List;

public record CodeEvaluationResponse(
    ScoreResponse correctness,
    ScoreResponse edgeCases,
    ScoreResponse efficiency,
    ScoreResponse codeQuality,
    List<String> risks,
    List<String> complexityNotes,
    String nextStep
) {}
