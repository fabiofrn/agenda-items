package com.fabio.agenda.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_agenda_item")
    private Long idAgendaItem;

    @Column
    private String cpf;

    @Column
    private char vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgendaItem() {
        return idAgendaItem;
    }

    public void setIdAgendaItem(Long idAgendaItem) {
        this.idAgendaItem = idAgendaItem;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public char getVote() {
        return vote;
    }

    public void setVote(char vote) {
        this.vote = vote;
    }
}

