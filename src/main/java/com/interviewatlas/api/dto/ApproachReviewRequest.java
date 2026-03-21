package com.interviewatlas.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ApproachReviewRequest(
    @NotBlank String problemStatement,
    @NotBlank String approach
) {}
