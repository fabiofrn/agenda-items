package com.fabio.agenda.service;

import com.fabio.agenda.repository.entity.VoteEntity;

public interface VoteService {

    VoteEntity vote(VoteEntity voteEntity) throws Exception;
}
