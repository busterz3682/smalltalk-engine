package com.taeyn.smalltalk.ai;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
    prefix = "app.ai",
    name = "provider",
    havingValue = "fake",
    matchIfMissing = true
)
public class FakeAiClient implements AiClient {

    @Override
    public String generateTopic(
        String situation,
        String category
    ) {
        return "요즘 새롭게 관심을 갖게 된 취미가 있나요?";
    }
}