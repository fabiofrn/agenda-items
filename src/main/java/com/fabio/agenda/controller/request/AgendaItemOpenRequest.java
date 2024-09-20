package com.fabio.agenda.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "Requisição para abrir uma pauta.")
public record AgendaItemOpenRequest(
        @Schema(description = "Quantidade em minutos do tempo em que a pauta permanecerá aberta para votação.",
                example = "10")
        @Positive(message = "O valor do campo 'minutes' deve ser maior que 0.")
        Integer minutes) {
}
