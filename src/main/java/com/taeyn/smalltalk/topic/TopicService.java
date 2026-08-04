package com.taeyn.smalltalk.topic;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicResponse create(CreateTopicRequest request) {
        Topic topic = new Topic(
                null,
                request.content(),
                request.situation(),
                request.category());

        Topic savedTopic = topicRepository.save(topic);

        return TopicResponse.from(savedTopic);
    }

    public List<TopicResponse> findAll() {
        return topicRepository.findAll()
                .stream()
                .map(TopicResponse::from)
                .toList();
    }
}