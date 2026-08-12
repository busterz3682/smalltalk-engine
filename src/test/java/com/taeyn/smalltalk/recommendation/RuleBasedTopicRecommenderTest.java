package com.taeyn.smalltalk.recommendation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.taeyn.smalltalk.topic.MemoryTopicRepository;
import com.taeyn.smalltalk.topic.Topic;
import com.taeyn.smalltalk.topic.TopicRepository;

class RuleBasedTopicRecommenderTest {

    @Test
    void 상황과_카테고리가_일치하는_topic을_추천한다() {
        TopicRepository repository = new MemoryTopicRepository();

        repository.save(new Topic(
            null,
            "처음 만난 사람과 이야기할 취미가 있나요?",
            "FIRST_MEETING",
            "HOBBY"
        ));

        repository.save(new Topic(
            null,
            "최근에 다녀온 여행지가 있나요?",
            "FIRST_MEETING",
            "TRAVEL"
        ));

        TopicRecommender recommender =
            new RuleBasedTopicRecommender(repository);

        RecommendedTopic recommended = recommender.recommend(
            "FIRST_MEETING",
            "HOBBY"
        );

        assertThat(recommended.content())
            .isEqualTo("처음 만난 사람과 이야기할 취미가 있나요?");

        assertThat(recommender.strategyName())
            .isEqualTo("RULE_BASED");
    }
}