package com.fabio.agenda.repository.projection;

import java.time.LocalDateTime;

public class VoteCountingImpl implements VoteCounting {

    private String description;

    private LocalDateTime sessionOpenTime;

    private LocalDateTime sessionCloseTime;

    private Long voteS;

    private Long voteN;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSessionOpenTime() {
        return sessionOpenTime;
    }

    public void setSessionOpenTime(LocalDateTime sessionOpenTime) {
        this.sessionOpenTime = sessionOpenTime;
    }

    public LocalDateTime getSessionCloseTime() {
        return sessionCloseTime;
    }

    public void setSessionCloseTime(LocalDateTime sessionCloseTime) {
        this.sessionCloseTime = sessionCloseTime;
    }

    public Long getVoteS() {
        return voteS;
    }

    public void setVoteS(Long voteS) {
        this.voteS = voteS;
    }

    public Long getVoteN() {
        return voteN;
    }

    public void setVoteN(Long voteN) {
        this.voteN = voteN;
    }
}


