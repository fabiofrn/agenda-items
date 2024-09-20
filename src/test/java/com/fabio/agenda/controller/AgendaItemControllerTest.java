package com.fabio.agenda.controller;

import com.fabio.agenda.controller.mapper.AgendaItemInputMapper;
import com.fabio.agenda.controller.request.AgendaItemRequest;
import com.fabio.agenda.controller.response.AgendaItemResponse;
import com.fabio.agenda.controller.response.VoteCountingResponse;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCountingImpl;
import com.fabio.agenda.service.AgendaItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AgendaItemController.class)
class AgendaItemControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AgendaItemService agendaItemService;

    @MockBean
    private AgendaItemInputMapper agendaItemInputMapper;

    private AgendaItemEntity agendaItem;

    private final AgendaItemRequest agendaItemRequest;

    private AgendaItemResponse agendaItemResponse;

    public AgendaItemControllerTest() {
        this.agendaItemRequest = new AgendaItemRequest("Pauta teste");
    }

    @BeforeEach
    void init() {
        this.agendaItem = new AgendaItemEntity();
        MockitoAnnotations.openMocks(this);
        when(agendaItemInputMapper.toEntity(any(AgendaItemRequest.class))).thenReturn(agendaItem);
        when(agendaItemInputMapper.toResponse(any(AgendaItemEntity.class))).thenReturn(agendaItemResponse);
    }

    @Test
    void insertTest() throws Exception {
        this.agendaItemResponse = new AgendaItemResponse(1L, "Pauta Test", "Pendente", null, null, null);
        when(agendaItemInputMapper.toResponse(any(AgendaItemEntity.class))).thenReturn(agendaItemResponse);
        when(agendaItemService.save(any())).thenReturn(agendaItem);
        mockMvc.perform(post("/v1/agenda-items")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(agendaItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sessionOpenTime").doesNotExist());
    }

    @Test
    void openTest() throws Exception {
        agendaItem.open(-1);
        this.agendaItemResponse = new AgendaItemResponse(1L, "Pauta Test", "Em Votação", "2024-01-01 09:35:00", "2024-01-01 10:35:00", 1);
        when(agendaItemService.open(any(), any())).thenReturn(agendaItem);
        when(agendaItemInputMapper.toResponse(any(AgendaItemEntity.class))).thenReturn(agendaItemResponse);
        mockMvc.perform(put("/v1/agenda-items/open/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionOpenTime").exists());
    }

    @Test
    void voteCountingTest() throws Exception {
        when(agendaItemService.voteCounting(any())).thenReturn(new VoteCountingImpl());
        when(agendaItemInputMapper.toResponse(any(VoteCountingImpl.class))).thenReturn(new VoteCountingResponse("Pauta Teste,", "2024-01-01 09:35:00", "2024-01-01 10:35:00", "Encerrada", 10, 15));

        mockMvc.perform(get("/v1/agenda-items/vote-counting/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voteS").value(10))
                .andExpect(jsonPath("$.voteN").value(15));
    }
}