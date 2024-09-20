package com.fabio.agenda.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Requisição para registrar o seu voto.")
public record VoteRequest(
        @Schema(description = "CPF do membro. Deve ser um CPF válido.",
                example = "12345678900")
        @CPF(message = "CPF inválido")
        String cpf,

        @Schema(description = "Identificador da pauta para a qual o voto é registrado. Deve ser fornecido e não pode ser nulo.",
                example = "456")
        @NotNull(message = "O campo 'identificador da pauta (id IdAgendaItem)' é obrigatório")
        Long idAgendaItem,

        @Schema(description = "Voto registrado. Deve ser 'S' para sim ou 'N' para não.",
                example = "S")
        @NotNull(message = "O campo 'voto (vote)' é obrigatório")
        @Pattern(regexp = "[SN]", message = "O campo 'voto' deve ser 'S' ou 'N'")
        String vote) {
}
