package com.interviewatlas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ClarificationRequest(
    @NotBlank String problemStatement,
    @NotBlank String question,
    int clarificationCount
) {}
