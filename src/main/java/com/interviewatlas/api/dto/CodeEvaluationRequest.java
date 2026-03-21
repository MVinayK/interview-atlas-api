package com.interviewatlas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CodeEvaluationRequest(
    @NotBlank String problemStatement,
    @NotBlank String approach,
    @NotBlank String language,
    @NotBlank String code
) {}
