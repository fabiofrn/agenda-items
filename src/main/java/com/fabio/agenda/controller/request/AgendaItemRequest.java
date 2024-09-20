package com.fabio.agenda.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para criar uma pauta.")
public record AgendaItemRequest(
        @Schema(description = "Descrição do pauta. Deve ser fornecida e não pode estar em branco.")
        @Size
        @NotBlank(message = "O campo 'Descrição (description)' é obrigatório")
        String description) {
}
