package com.taeyn.smalltalk.recommendation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.taeyn.smalltalk.ai.AiClient;

@Component
@ConditionalOnProperty(
    prefix = "app.recommendation",
    name = "strategy",
    havingValue = "ai"
)
public class AiTopicRecommender
        implements TopicRecommender {

    private final AiClient aiClient;

    public AiTopicRecommender(
        AiClient aiClient
    ) {
        this.aiClient = aiClient;
    }

    @Override
    public RecommendedTopic recommend(
        String situation,
        String category
    ) {
        String generatedContent =
            aiClient.generateTopic(
                situation,
                category
            );

        return RecommendedTopic.generated(
            generatedContent,
            situation,
            category
        );
    }

    @Override
    public String strategyName() {
        return "AI";
    }
}