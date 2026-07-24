package com.taeyn.smalltalk.topic;

import jakarta.validation.constraints.NotBlank;

public record CreateTopicRequest(

        @NotBlank String content,

        @NotBlank String situation,

        @NotBlank String category) {
}