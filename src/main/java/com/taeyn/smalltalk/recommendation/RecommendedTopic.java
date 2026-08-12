package com.taeyn.smalltalk.recommendation;

import com.taeyn.smalltalk.topic.Topic;

public record RecommendedTopic(
    Long topicId,
    String content,
    String situation,
    String category
) {

    public static RecommendedTopic from(Topic topic) {
        return new RecommendedTopic(
            topic.id(),
            topic.content(),
            topic.situation(),
            topic.category()
        );
    }

    public static RecommendedTopic generated(
        String content,
        String situation,
        String category
    ) {
        return new RecommendedTopic(
            null,
            content,
            situation,
            category
        );
    }
}