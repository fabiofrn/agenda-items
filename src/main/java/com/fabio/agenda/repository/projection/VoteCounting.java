package com.fabio.agenda.repository.projection;

import java.time.LocalDateTime;

public interface VoteCounting {

    String getDescription();

    LocalDateTime getSessionOpenTime();

    LocalDateTime getSessionCloseTime();

    Long getVoteS();

    Long getVoteN();
}

