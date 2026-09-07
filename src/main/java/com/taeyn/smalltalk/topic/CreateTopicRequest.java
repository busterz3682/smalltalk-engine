package com.taeyn.smalltalk.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTopicRequest(

        @NotBlank(message = "주제 내용은 필수입니다.")
        @Size(max = 300, message = "주제 내용은 300자 이하여야 합니다.")
        String content,

        @NotBlank(message = "상황은 필수입니다.")
        @Size(max = 100, message = "상황은 100자 이하여야 합니다.")
        String situation,

        @NotBlank(message = "카테고리는 필수입니다.")
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
        String category) {
}
