package com.taeyn.smalltalk.recommendation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.taeyn.smalltalk.topic.Topic;
import com.taeyn.smalltalk.topic.TopicRepository;

@Component
@ConditionalOnProperty(
    prefix = "app.recommendation",
    name = "strategy",
    havingValue = "random"
)
public class RandomTopicRecommender
        implements TopicRecommender {

    private final TopicRepository topicRepository;

    public RandomTopicRecommender(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    @Override
    public RecommendedTopic recommend(
        String situation,
        String category
    ) {
        List<Topic> candidates = topicRepository.findAll()
            .stream()
            .filter(topic ->
                topic.situation().equalsIgnoreCase(situation)
            )
            .filter(topic ->
                topic.category().equalsIgnoreCase(category)
            )
            .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "조건에 맞는 추천 주제가 없습니다."
            );
        }

        int randomIndex = ThreadLocalRandom.current()
            .nextInt(candidates.size());

        Topic selectedTopic = candidates.get(randomIndex);

        return RecommendedTopic.from(selectedTopic);
    }

    @Override
    public String strategyName() {
        return "RANDOM";
    }
}