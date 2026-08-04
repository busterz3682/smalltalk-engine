package com.taeyn.smalltalk.recommendation;

import org.springframework.stereotype.Service;

import com.taeyn.smalltalk.topic.Topic;

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
        Topic topic = topicRecommender.recommend(
            request.situation(),
            request.category()
        );

        return RecommendationResponse.from(
            topic,
            topicRecommender.strategyName()
        );
    }
}