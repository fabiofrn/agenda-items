package com.fabio.agenda.service;

import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCounting;

public interface AgendaItemService {

    AgendaItemEntity save(AgendaItemEntity agendaItemEntity);

    AgendaItemEntity findById(Long id);

    AgendaItemEntity open(Long id, Integer hour);

    VoteCounting voteCounting(Long id);

}
