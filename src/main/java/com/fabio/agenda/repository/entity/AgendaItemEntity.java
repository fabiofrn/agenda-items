package com.fabio.agenda.repository.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "agenda_items")
public class AgendaItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDateTime sessionOpenTime;

    @Column
    private LocalDateTime sessionCloseTime;

    public Long getId() {
        return id;
    }

    public LocalDateTime getSessionCloseTime() {
        return sessionCloseTime;
    }

    public LocalDateTime getSessionOpenTime() {
        return sessionOpenTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void open(Integer minutes) {
        this.sessionOpenTime = LocalDateTime.now();
        this.sessionCloseTime = this.sessionOpenTime.plusMinutes(minutes);
    }

    public boolean isPending() {
        return this.sessionCloseTime == null;
    }

    public boolean isInProgress() {
        return this.sessionCloseTime != null && this.sessionCloseTime.isAfter(LocalDateTime.now());
    }

    public boolean isExpired() {
        return this.sessionCloseTime != null && LocalDateTime.now().isAfter(this.sessionCloseTime);
    }

}

