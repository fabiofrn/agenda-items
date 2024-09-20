package com.fabio.agenda.service.impl;

import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.exceptions.NotFoundException;
import com.fabio.agenda.repository.AgendaItemRepository;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCounting;
import com.fabio.agenda.service.AgendaItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class AgendaItemServiceImpl implements AgendaItemService {

    private final AgendaItemRepository agendaItemRepository;

    @Autowired
    public AgendaItemServiceImpl(AgendaItemRepository agendaItemRepository) {
        this.agendaItemRepository = agendaItemRepository;
    }

    @Override
    public AgendaItemEntity save(AgendaItemEntity agendaItemEntity) {
        return agendaItemRepository.save(agendaItemEntity);
    }

    @Override
    public AgendaItemEntity findById(Long id) {
        return agendaItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Pauta com id {0} não encontrada!", id)));
    }

    @Override
    @Transactional
    public AgendaItemEntity open(Long id, Integer minutes) {
        AgendaItemEntity agendaItemSaved = findById(id);
        if (agendaItemSaved.isInProgress()) {
            throw new BusinessException(MessageFormat.format("A pauta {0} já esta em votação!", agendaItemSaved.getDescription()));
        }

        if (agendaItemSaved.isExpired()) {
            throw new BusinessException(MessageFormat.format("A pauta {0} já foi fechada!", agendaItemSaved.getDescription()));
        }
        agendaItemSaved.open(minutes);
        return agendaItemRepository.save(agendaItemSaved);
    }

    @Override
    public VoteCounting voteCounting(Long id) {
        AgendaItemEntity agendaItemSaved = findById(id);
        if (agendaItemSaved.isInProgress() || agendaItemSaved.isPending()) {
            throw new BusinessException(MessageFormat.format("A pauta {0} ainda não foi votada ou aberta para votação!", agendaItemSaved.getDescription()));
        }
        return agendaItemRepository.voteCounting(id);
    }
}
