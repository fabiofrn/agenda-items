package com.fabio.agenda.service.impl;


import com.fabio.agenda.exceptions.ApplicationException;
import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.repository.VoteRepository;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.entity.VoteEntity;
import com.fabio.agenda.service.AgendaItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class VoteServiceTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AgendaItemService agendaItemService;

    private final VoteEntity voteEntity;

    private final AgendaItemEntity agendaItem;

    public VoteServiceTest() {

        agendaItem = new AgendaItemEntity();
        agendaItem.setDescription("Pauta Teste");

        voteEntity = new VoteEntity();
        voteEntity.setVote('S');
        voteEntity.setIdAgendaItem(1L);
    }

    @BeforeEach
    void beforeEach() {
        openMocks(this);
        when(agendaItemService.findById(anyLong())).thenReturn(agendaItem);
    }

    @Test
    void voteSuccessTest() {
        when(voteRepository.save(any())).thenReturn(new VoteEntity());
        agendaItem.open(1);
        assertDoesNotThrow(() -> voteService.vote(voteEntity));
    }

    @Test
    void voteSuccessExceptionPendentTest() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> voteService.vote(voteEntity));
        assertEquals("A pauta Pauta Teste ainda não foi iniciada!", businessException.getMessage());
    }

    @Test
    void voteSuccessExceptionClosedTest() {
        agendaItem.open(-1);
        BusinessException businessException = assertThrows(BusinessException.class, () -> voteService.vote(voteEntity));
        assertEquals("A pauta Pauta Teste já foi encerrada!", businessException.getMessage());
    }

    @Test
    void voteGenericExceptionTest() {
        agendaItem.open(1);
        ReflectionTestUtils.setField(voteService, "voteRepository", null);
        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> voteService.vote(voteEntity));
        assertEquals("Cannot invoke \"com.fabio.agenda.repository.VoteRepository.save(Object)\" because \"this.voteRepository\" is null", applicationException.getMessage());
    }

    @Test
    void voteIntegrityExceptionTest() {
        agendaItem.open(1);
        when(voteRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        BusinessException businessException = assertThrows(BusinessException.class, () -> voteService.vote(voteEntity));
        assertEquals("Você já votou para esta pauta!", businessException.getMessage());
    }

}