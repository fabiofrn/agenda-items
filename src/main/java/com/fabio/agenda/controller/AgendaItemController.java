package com.fabio.agenda.controller;

import com.fabio.agenda.controller.mapper.AgendaItemInputMapper;
import com.fabio.agenda.controller.request.AgendaItemOpenRequest;
import com.fabio.agenda.controller.request.AgendaItemRequest;
import com.fabio.agenda.controller.response.AgendaItemResponse;
import com.fabio.agenda.controller.response.VoteCountingResponse;
import com.fabio.agenda.exceptions.domain.ApiErro;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCounting;
import com.fabio.agenda.service.AgendaItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/agenda-items")
@Tag(name = "Pautas", description = "Endpoints para gerenciamento de pautas")
public class AgendaItemController {

    private final AgendaItemService agendaItemService;

    private final AgendaItemInputMapper agendaItemInputMapper;

    @Autowired
    public AgendaItemController(AgendaItemService agendaItemService, AgendaItemInputMapper agendaItemInputMapper) {
        this.agendaItemService = agendaItemService;
        this.agendaItemInputMapper = agendaItemInputMapper;
    }

    @PostMapping
    @Operation(summary = "Criar pauta", description = "Cria uma pauta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso.",
                    content = @Content(schema = @Schema(implementation = AgendaItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validaçao de algum atributo",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    public ResponseEntity<AgendaItemResponse> insert(@Valid @RequestBody AgendaItemRequest agendaItemRequest) {
        AgendaItemEntity agendaItem = agendaItemService.save(agendaItemInputMapper.toEntity(agendaItemRequest));
        return new ResponseEntity<>(agendaItemInputMapper.toResponse(agendaItem), HttpStatus.CREATED);
    }

    @PutMapping("/open/{id}")
    @Operation(summary = "Abrir item da agenda", description = "Abre uma pauta especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta aberta com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendaItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    public ResponseEntity<AgendaItemResponse> open(@PathVariable Long id, @Valid @RequestBody(required = false) AgendaItemOpenRequest agendaItemOpenRequest) {
        if (agendaItemOpenRequest == null) {
            agendaItemOpenRequest = new AgendaItemOpenRequest(1);
        }
        AgendaItemEntity agendaItemEntity = agendaItemService.open(id, agendaItemOpenRequest.minutes());
        return new ResponseEntity<>(agendaItemInputMapper.toResponse(agendaItemEntity), HttpStatus.OK);
    }

    @GetMapping("/vote-counting/{id}")
    @Operation(summary = "Contabilizar votos de uma pauta", description = "Realiza a contagem dos votos para uma pauta especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Votos contabilizados com sucesso",
                    content = @Content(schema = @Schema(implementation = VoteCountingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    public ResponseEntity<VoteCountingResponse> voteCounting(@PathVariable Long id) {
        VoteCounting voteCounting = agendaItemService.voteCounting(id);
        return new ResponseEntity<>(agendaItemInputMapper.toResponse(voteCounting), HttpStatus.OK);
    }
}
