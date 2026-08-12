package com.taeyn.smalltalk.recommendation;

public interface TopicRecommender {

    RecommendedTopic recommend(
        String situation,
        String category
    );

    String strategyName();
}