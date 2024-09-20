package com.fabio.agenda.controller.mapper;

import com.fabio.agenda.controller.request.VoteRequest;
import com.fabio.agenda.controller.response.VoteResponse;
import com.fabio.agenda.repository.VoteYesOrNo;
import com.fabio.agenda.repository.entity.VoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VoteInputMapper {

    VoteEntity toEntity(VoteRequest voteRequest);

    @Mapping(source = "vote", target = "vote", qualifiedByName = "codeToDescription")
    VoteResponse toResponse(VoteEntity voteEntity);

    @Named("codeToDescription")
    default String codeToDescription(String code) {
        if (code == null) {
            return null;
        }
        for (VoteYesOrNo vote : VoteYesOrNo.values()) {
            if (vote.getCode().equals(code)) {
                return vote.getDescription();
            }
        }
        throw new IllegalArgumentException("Código de voto desconhecido: " + code);
    }

}
