package com.taeyn.smalltalk.topic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class MemoryTopicRepositoryTest {

    @Test
    void topic을_저장하고_전체_조회할_수_있다() {
        MemoryTopicRepository repository =
            new MemoryTopicRepository();

        Topic topic = new Topic(
            null,
            "최근에 재미있게 본 콘텐츠가 있나요?",
            "FIRST_MEETING",
            "HOBBY"
        );

        Topic savedTopic = repository.save(topic);
        List<Topic> topics = repository.findAll();

        assertThat(savedTopic.id()).isEqualTo(1L);
        assertThat(topics).hasSize(1);
        assertThat(topics.get(0)).isEqualTo(savedTopic);
    }
}