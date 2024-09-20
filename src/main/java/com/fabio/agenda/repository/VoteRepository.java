package com.fabio.agenda.repository;

import com.fabio.agenda.repository.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Long> {
}
