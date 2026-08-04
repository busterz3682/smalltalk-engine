package com.taeyn.smalltalk.topic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TopicServiceTest {

    @Test
    void topic을_생성할_수_있다() {
        TopicRepository repository = new MemoryTopicRepository();
        TopicService service = new TopicService(repository);

        CreateTopicRequest request = new CreateTopicRequest(
            "최근에 재미있게 본 콘텐츠가 있나요?",
            "FIRST_MEETING",
            "HOBBY"
        );

        TopicResponse response = service.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.content())
            .isEqualTo("최근에 재미있게 본 콘텐츠가 있나요?");
        assertThat(response.situation())
            .isEqualTo("FIRST_MEETING");
        assertThat(response.category())
            .isEqualTo("HOBBY");
    }
}