package com.taeyn.smalltalk.recommendation;

public record RecommendationResponse(
    Long topicId,
    String content,
    String situation,
    String category,
    String strategy
) {

    public static RecommendationResponse from(
        RecommendedTopic topic,
        String strategy
    ) {
        return new RecommendationResponse(
            topic.topicId(),
            topic.content(),
            topic.situation(),
            topic.category(),
            strategy
        );
    }
}