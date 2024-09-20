package com.fabio.agenda.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta que representa um voto.")
public record VoteResponse(
        @Schema(description = "Identificador único do voto.", example = "1") Long id,
        @Schema(description = "Valor do voto. Pode ser 'S' para sim ou 'N' para não.", example = "S") String vote) {
}
