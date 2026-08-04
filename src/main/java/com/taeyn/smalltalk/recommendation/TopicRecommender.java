package com.taeyn.smalltalk.recommendation;

import com.taeyn.smalltalk.topic.Topic;

public interface TopicRecommender {

    Topic recommend(String situation, String category);

    String strategyName();
}