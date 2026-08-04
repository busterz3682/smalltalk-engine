package com.taeyn.smalltalk.recommendation;

import jakarta.validation.constraints.NotBlank;

public record RecommendationRequest(
    @NotBlank String situation,
    @NotBlank String category
) {
}