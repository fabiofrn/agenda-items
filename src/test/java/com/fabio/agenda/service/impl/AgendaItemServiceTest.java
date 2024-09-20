package com.fabio.agenda.service.impl;

import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.exceptions.NotFoundException;
import com.fabio.agenda.repository.AgendaItemRepository;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCountingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AgendaItemServiceTest {

    @InjectMocks
    private AgendaItemServiceImpl agendaItemService;

    @Mock
    private AgendaItemRepository agendaItemRepository;

    private AgendaItemEntity agendaItemEntity;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.agendaItemEntity = createAgenteItemEntity();
        when(agendaItemRepository.findById(anyLong())).thenReturn(Optional.of(agendaItemEntity));
    }

    @Test
    void findByIdTest() {
        assertDoesNotThrow(() -> agendaItemService.findById(1L));
    }

    @Test
    void findByIdNotFoundExceptionTest() {
        when(agendaItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> agendaItemService.findById(1L));
        assertEquals("Pauta com id 1 não encontrada!", notFoundException.getMessage());
    }

    @Test
    void insertTest() {
        when(agendaItemRepository.save(any())).thenReturn(agendaItemEntity);
        assertDoesNotThrow(() -> agendaItemService.save(agendaItemEntity));
    }

    @Test
    void openTest() {
        when(agendaItemRepository.save(any())).thenReturn(agendaItemEntity);
        AgendaItemEntity agendaItemOpenEntity = agendaItemService.open(1L, 1);
        assertTrue(agendaItemOpenEntity.isInProgress());
    }

    @Test
    void openIsProgressExceptionTest() {
        agendaItemEntity.open(1);
        BusinessException businessException = assertThrows(BusinessException.class, () -> agendaItemService.open(1L, 1));
        assertEquals("A pauta Pauta Teste já esta em votação!", businessException.getMessage());
    }

    @Test
    void openIsClosedExceptionTest() {
        when(agendaItemRepository.save(any())).thenReturn(agendaItemEntity);
        ReflectionTestUtils.setField(agendaItemEntity, "sessionCloseTime", LocalDateTime.now().minusMinutes(1));
        BusinessException businessException = assertThrows(BusinessException.class, () -> agendaItemService.open(1L, 1));
        assertEquals("A pauta Pauta Teste já foi fechada!", businessException.getMessage());
    }

    @Test
    void voteCountingTest() {
        agendaItemEntity.open(-1);
        when(agendaItemRepository.voteCounting(anyLong())).thenReturn(new VoteCountingImpl());
        assertDoesNotThrow(() -> agendaItemService.voteCounting(1L));
    }

    @Test
    void voteCountingNotClosedBusinessPendingExceptionTest() {
        agendaItemEntity.open(1);
        BusinessException businessException = assertThrows(BusinessException.class, () -> agendaItemService.voteCounting(1L));
        assertEquals("A pauta Pauta Teste ainda não foi votada ou aberta para votação!", businessException.getMessage());
    }

    @Test
    void voteCountingNotCloedBusinessExpiredExceptionTest() {
        ReflectionTestUtils.setField(agendaItemEntity, "sessionOpenTime", LocalDateTime.now().minusMinutes(1));
        BusinessException businessException = assertThrows(BusinessException.class, () -> agendaItemService.voteCounting(1L));
        assertEquals("A pauta Pauta Teste ainda não foi votada ou aberta para votação!", businessException.getMessage());
    }

    private static AgendaItemEntity createAgenteItemEntity() {
        AgendaItemEntity agendaItemEntity = new AgendaItemEntity();
        agendaItemEntity.setDescription("Pauta Teste");
        return agendaItemEntity;
    }
}