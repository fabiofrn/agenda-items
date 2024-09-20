package com.fabio.agenda.service.impl;

import com.fabio.agenda.exceptions.ApplicationException;
import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.repository.VoteRepository;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.entity.VoteEntity;
import com.fabio.agenda.service.AgendaItemService;
import com.fabio.agenda.service.VoteService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final AgendaItemService agendaItemService;

    public VoteServiceImpl(VoteRepository voteRepository, AgendaItemService agendaItemService) {
        this.voteRepository = voteRepository;
        this.agendaItemService = agendaItemService;
    }

    @Override
    @Transactional
    public VoteEntity vote(VoteEntity voteEntity) {
        AgendaItemEntity agendaItem = agendaItemService.findById(voteEntity.getIdAgendaItem());

        if (agendaItem.isPending()) {
            throw new BusinessException(MessageFormat.format("A pauta {0} ainda não foi iniciada!", agendaItem.getDescription()));
        }

        if (agendaItem.isExpired()) {
            throw new BusinessException(MessageFormat.format("A pauta {0} já foi encerrada!", agendaItem.getDescription()));
        }

        try {
            return voteRepository.save(voteEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new BusinessException("Você já votou para esta pauta!");
        } catch (Exception exception) {
            throw new ApplicationException(exception.getMessage());
        }
    }
}
