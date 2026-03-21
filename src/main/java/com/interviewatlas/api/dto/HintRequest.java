package com.interviewatlas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record HintRequest(
    @NotBlank String problemStatement,
    @NotBlank String approach,
    int hintLevel
) {}
