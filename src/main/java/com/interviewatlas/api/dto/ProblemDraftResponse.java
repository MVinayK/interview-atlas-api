package com.interviewatlas.api.dto;

import java.util.List;

public record ProblemDraftResponse(
    String statement,
    String constraints,
    String inputFormat,
    String outputFormat,
    List<ProblemExampleResponse> examples,
    String edgeCaseHint
) {}
