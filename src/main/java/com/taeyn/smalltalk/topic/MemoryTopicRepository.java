package com.taeyn.smalltalk.topic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class MemoryTopicRepository implements TopicRepository {

    private final List<Topic> topics = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Topic save(Topic topic) {
        Long id = sequence.incrementAndGet();

        Topic savedTopic = new Topic(
            id,
            topic.content(),
            topic.situation(),
            topic.category()
        );

        topics.add(savedTopic);

        return savedTopic;
    }

    @Override
    public List<Topic> findAll() {
        return List.copyOf(topics);
    }
}