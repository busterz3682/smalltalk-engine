package com.taeyn.smalltalk.recommendation;

import com.taeyn.smalltalk.topic.Topic;

public record RecommendationResponse(
    Long topicId,
    String content,
    String situation,
    String category,
    String strategy
) {

    public static RecommendationResponse from(
        Topic topic,
        String strategy
    ) {
        return new RecommendationResponse(
            topic.id(),
            topic.content(),
            topic.situation(),
            topic.category(),
            strategy
        );
    }
}