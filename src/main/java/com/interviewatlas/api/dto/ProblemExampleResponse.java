package com.interviewatlas.api.dto;

public record ProblemExampleResponse(
    String input,
    String output,
    String explanation
) {}
