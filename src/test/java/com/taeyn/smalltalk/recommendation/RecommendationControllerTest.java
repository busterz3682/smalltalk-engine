package com.taeyn.smalltalk.recommendation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.taeyn.smalltalk.ai.AiServiceException;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecommendationService recommendationService;

    @Test
    void 추천을_요청하면_추천_주제를_반환한다() throws Exception {
        RecommendationRequest request = new RecommendationRequest(
            "회사 점심시간에 처음 만난 동료와 대화",
            "일상"
        );
        RecommendationResponse response = new RecommendationResponse(
            null,
            "요즘 점심시간에는 주로 어떻게 보내세요?",
            request.situation(),
            request.category(),
            "AI"
        );

        given(recommendationService.recommend(request)).willReturn(response);

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "situation": "회사 점심시간에 처음 만난 동료와 대화",
                      "category": "일상"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content")
                .value("요즘 점심시간에는 주로 어떻게 보내세요?"))
            .andExpect(jsonPath("$.strategy").value("AI"));
    }

    @Test
    void 상황이_비어_있으면_구체적인_400_응답을_반환한다() throws Exception {
        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "situation": "",
                      "category": "일상"
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
            .andExpect(jsonPath("$.message").value("situation: 상황은 필수입니다."))
            .andExpect(jsonPath("$.path").value("/api/recommendations"));
    }

    @Test
    void AI_호출이_실패하면_503을_반환한다() throws Exception {
        RecommendationRequest request = new RecommendationRequest(
            "회사 점심시간에 처음 만난 동료와 대화",
            "일상"
        );

        given(recommendationService.recommend(request))
            .willThrow(new AiServiceException(
                "AI 추천 서비스를 일시적으로 사용할 수 없습니다.",
                new RuntimeException("외부 서비스 오류")
            ));

        mockMvc.perform(post("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "situation": "회사 점심시간에 처음 만난 동료와 대화",
                      "category": "일상"
                    }
                    """))
            .andExpect(status().isServiceUnavailable())
            .andExpect(jsonPath("$.code").value("AI_SERVICE_UNAVAILABLE"))
            .andExpect(jsonPath("$.message")
                .value("AI 추천 서비스를 일시적으로 사용할 수 없습니다."));
    }
}
