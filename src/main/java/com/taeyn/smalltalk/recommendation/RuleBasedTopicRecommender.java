package com.taeyn.smalltalk.recommendation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.taeyn.smalltalk.topic.Topic;
import com.taeyn.smalltalk.topic.TopicRepository;

@Component
public class RuleBasedTopicRecommender
        implements TopicRecommender {

    private final TopicRepository topicRepository;

    public RuleBasedTopicRecommender(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic recommend(
        String situation,
        String category
    ) {
        return topicRepository.findAll()
            .stream()
            .filter(topic ->
                topic.situation().equalsIgnoreCase(situation)
            )
            .filter(topic ->
                topic.category().equalsIgnoreCase(category)
            )
            .findFirst()
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "조건에 맞는 추천 주제가 없습니다."
                )
            );
    }

    @Override
    public String strategyName() {
        return "RULE_BASED";
    }
}