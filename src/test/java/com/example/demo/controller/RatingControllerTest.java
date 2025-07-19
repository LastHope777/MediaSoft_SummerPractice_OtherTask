package com.example.demo.controller;

import com.example.demo.dto.RatingRequestDTO;
import com.example.demo.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createReview_shouldReturnOk() throws Exception {
        RatingRequestDTO dto = new RatingRequestDTO(1L, 2L, 4, "Хорошо");

        mockMvc.perform(post("/api/reviews")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getReviews_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void updateReview_shouldReturnOk() throws Exception {
        RatingRequestDTO dto = new RatingRequestDTO(1L, 2L, 5, "Отлично");

        mockMvc.perform(put("/api/reviews/1/2")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteReview_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/reviews/1/2"))
                .andExpect(status().isOk());
    }
}
