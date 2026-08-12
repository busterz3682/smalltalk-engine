package com.taeyn.smalltalk.recommendation;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final TopicRecommender topicRecommender;

    public RecommendationService(
        TopicRecommender topicRecommender
    ) {
        this.topicRecommender = topicRecommender;
    }

    public RecommendationResponse recommend(
        RecommendationRequest request
    ) {
        RecommendedTopic topic =
            topicRecommender.recommend(
                request.situation(),
                request.category()
            );

        return RecommendationResponse.from(
            topic,
            topicRecommender.strategyName()
        );
    }
}