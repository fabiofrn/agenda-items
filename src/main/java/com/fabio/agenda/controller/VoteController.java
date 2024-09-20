package com.fabio.agenda.controller;

import com.fabio.agenda.controller.mapper.VoteInputMapper;
import com.fabio.agenda.controller.request.VoteRequest;
import com.fabio.agenda.controller.response.VoteResponse;
import com.fabio.agenda.exceptions.domain.ApiErro;
import com.fabio.agenda.repository.entity.VoteEntity;
import com.fabio.agenda.service.VoteService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/votes")
@Tag(name = "Votos", description = "Endpoints para votar a pauta")
public class VoteController {

    private final VoteService voteService;

    private final VoteInputMapper voteInputMapper;

    @Autowired
    public VoteController(VoteService voteService, VoteInputMapper voteInputMapper) {
        this.voteService = voteService;
        this.voteInputMapper = voteInputMapper;
    }

    @PostMapping
    @Operation(summary = "Votar", description = "Registra um novo voto e retorna a resposta com os detalhes do voto cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = VoteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    public ResponseEntity<VoteResponse> insert(@Valid @RequestBody VoteRequest voteRequest) throws Exception {
        VoteEntity voteEntity = voteService.vote(voteInputMapper.toEntity(voteRequest));
        return new ResponseEntity<>(voteInputMapper.toResponse(voteEntity), HttpStatus.CREATED);
    }
}
