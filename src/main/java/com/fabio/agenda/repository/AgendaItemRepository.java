package com.fabio.agenda.repository;

import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCounting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaItemRepository extends CrudRepository<AgendaItemEntity, Long> {

    @Query(value = "select " +
            "ai.description, " +
            "ai.session_open_time, " +
            "ai.session_close_time, " +
            "COUNT(CASE WHEN v.vote = 'S' THEN 1 END) AS vote_s, " +
            "COUNT(CASE WHEN v.vote = 'N' THEN 1 END) AS vote_n " +
            "from agenda_items ai " +
            "left join votes v on ai.id = v.id_agenda_item  " +
            "WHERE ai.id = :agendaItemId " +
            "group by ai.description, " +
            "ai.session_open_time, " +
            "ai.session_close_time ", nativeQuery = true)
    VoteCounting voteCounting(Long agendaItemId);
}
