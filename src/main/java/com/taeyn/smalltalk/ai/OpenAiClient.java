package com.taeyn.smalltalk.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

@Component
@ConditionalOnProperty(
    prefix = "app.ai",
    name = "provider",
    havingValue = "openai"
)
public class OpenAiClient implements AiClient {

    private static final String INSTRUCTIONS = """
        당신은 상황과 카테고리에 맞는 자연스러운 스몰토크 질문을 생성합니다.
        질문은 한국어로 하나만 출력하고, 부가 설명은 하지 마세요.
        """;

    private final OpenAIClient client;
    private final String model;

    public OpenAiClient(
        @Value("${app.ai.openai.model}") String model
    ) {
        this.client = OpenAIOkHttpClient.fromEnv();
        this.model = model;
    }

    @Override
    public String generateTopic(
        String situation,
        String category
    ) {
        String input = """
            상황: %s
            카테고리: %s
            """.formatted(situation, category);

        ResponseCreateParams params =
            ResponseCreateParams.builder()
                .model(model)
                .instructions(INSTRUCTIONS)
                .input(input)
                .build();

        try {
            Response response = client.responses().create(params);

            return response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(outputText -> outputText.text().trim())
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() ->
                    new AiServiceException(
                        "AI가 추천 주제를 생성하지 못했습니다.",
                        null
                    )
                );
        } catch (AiServiceException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            throw new AiServiceException(
                "AI 추천 서비스를 일시적으로 사용할 수 없습니다.",
                exception
            );
        }
    }
}
