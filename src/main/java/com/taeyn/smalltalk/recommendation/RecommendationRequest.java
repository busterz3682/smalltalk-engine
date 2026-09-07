package com.taeyn.smalltalk.recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecommendationRequest(
    @NotBlank(message = "상황은 필수입니다.")
    @Size(max = 100, message = "상황은 100자 이하여야 합니다.")
    String situation,

    @NotBlank(message = "카테고리는 필수입니다.")
    @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
    String category
) {
}
