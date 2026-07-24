package com.taeyn.smalltalk.topic;

public record TopicResponse(
    Long id,
    String content,
    String situation,
    String category
) {

    public static TopicResponse from(Topic topic) {
        return new TopicResponse(
            topic.id(),
            topic.content(),
            topic.situation(),
            topic.category()
        );
    }
}