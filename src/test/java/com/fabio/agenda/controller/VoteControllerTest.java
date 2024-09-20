package com.fabio.agenda.controller;

import com.fabio.agenda.controller.mapper.VoteInputMapper;
import com.fabio.agenda.controller.request.VoteRequest;
import com.fabio.agenda.controller.response.VoteResponse;
import com.fabio.agenda.repository.entity.VoteEntity;
import com.fabio.agenda.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
class VoteControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    @MockBean
    private VoteInputMapper voteInputMapper;

    @Test
    void insertTest() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        VoteRequest voteRequest = new VoteRequest("21533900019", 1L, "S");

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setId(1L);
        when(voteService.vote(any())).thenReturn(voteEntity);
        when(voteInputMapper.toEntity(any())).thenReturn(voteEntity);
        when(voteInputMapper.toResponse(any())).thenReturn(new VoteResponse(1L, "Sim"));

        mockMvc.perform(post("/v1/votes")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(voteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.vote").value("Sim"));

    }


}