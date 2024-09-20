package com.fabio.agenda.exceptions.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErro(
        LocalDateTime hora,
        String mensagem,
        String detalhes
) {
}
