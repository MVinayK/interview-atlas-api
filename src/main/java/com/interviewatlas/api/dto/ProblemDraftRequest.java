package com.interviewatlas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ProblemDraftRequest(
    @NotBlank String company,
    @NotBlank String title,
    String difficulty,
    String sourcePrompt
) {}
