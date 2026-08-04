package com.taeyn.smalltalk.topic;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TopicService topicService;

    @Test
    void topic을_등록하면_201과_등록된_topic을_반환한다() throws Exception {
        TopicResponse response = new TopicResponse(
            1L,
            "최근에 재미있게 본 콘텐츠가 있나요?",
            "FIRST_MEETING",
            "HOBBY"
        );

        given(topicService.create(
            new CreateTopicRequest(
                "최근에 재미있게 본 콘텐츠가 있나요?",
                "FIRST_MEETING",
                "HOBBY"
            )
        )).willReturn(response);

        mockMvc.perform(
                post("/api/topics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                          "content": "최근에 재미있게 본 콘텐츠가 있나요?",
                          "situation": "FIRST_MEETING",
                          "category": "HOBBY"
                        }
                        """)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.content")
                .value("최근에 재미있게 본 콘텐츠가 있나요?"))
            .andExpect(jsonPath("$.situation")
                .value("FIRST_MEETING"))
            .andExpect(jsonPath("$.category")
                .value("HOBBY"));
    }

    @Test
    void topic_목록을_조회하면_200과_목록을_반환한다() throws Exception {
        List<TopicResponse> responses = List.of(
            new TopicResponse(
                1L,
                "최근에 재미있게 본 콘텐츠가 있나요?",
                "FIRST_MEETING",
                "HOBBY"
            ),
            new TopicResponse(
                2L,
                "요즘 즐겨 듣는 음악이 있나요?",
                "FIRST_MEETING",
                "MUSIC"
            )
        );

        given(topicService.findAll()).willReturn(responses);

        mockMvc.perform(
                get("/api/topics")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].content")
                .value("최근에 재미있게 본 콘텐츠가 있나요?"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].category").value("MUSIC"));
    }
    @Test
    void content가_비어_있으면_400을_반환한다() throws Exception {
        mockMvc.perform(
                post("/api/topics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {
                        "content": "",
                        "situation": "FIRST_MEETING",
                        "category": "HOBBY"
                        }
                        """)
            )
            .andExpect(status().isBadRequest());
    }
}