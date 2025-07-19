package com.example.demo.controller;

import com.example.demo.dto.VisitorRequestDTO;
import com.example.demo.service.VisitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postVisitor_shouldReturnOk() throws Exception {
        VisitorRequestDTO dto = new VisitorRequestDTO("Тест", 22, "М");
        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getVisitors_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteVisitor_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}
