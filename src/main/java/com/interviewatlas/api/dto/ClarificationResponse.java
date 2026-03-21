package com.interviewatlas.api.dto;

public record ClarificationResponse(
    String answer,
    boolean clarificationLimitReached
) {}
