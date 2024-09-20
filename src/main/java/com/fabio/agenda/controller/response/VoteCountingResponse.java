package com.fabio.agenda.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta que representa a contagem de votos.")
public record VoteCountingResponse(
        @Schema(description = "Descrição da pauta ou sessão de votação.") String description,
        @Schema(description = "Hora em que a sessão foi aberta.", example = "2024-09-20 09:00:00") String sessionOpenTime,
        @Schema(description = "Hora em que a sessão foi fechada.", example = "2024-09-20 17:00:00") String sessionCloseTime,
        @Schema(description = "Status atual da sessão de votação.", example = "Encerrada") String status,
        @Schema(description = "Número de votos positivos.", example = "120") int voteS,
        @Schema(description = "Número de votos negativos.", example = "30") int voteN) {
}

