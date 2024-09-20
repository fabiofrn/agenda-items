package com.fabio.agenda.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta que representa uma pauta.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgendaItemResponse(
        @Schema(description = "Identificador único da pauta.") Long id,
        @Schema(description = "Descrição do item da pauta.") String description,
        @Schema(description = "Status atual da pauta.", example = "Pendente") String status,
        @Schema(description = "Hora em que a sessão foi aberta.", example = "2024-09-20 09:00:00") String sessionOpenTime,
        @Schema(description = "Hora em que a sessão será fechada.", example = "2024-09-20 17:00:00") String sessionCloseTime,
        @Schema(description = "Número de minutos em que a agenda ficará para votação.", example = "8") Integer minutes) {
}
