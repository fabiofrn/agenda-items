package com.fabio.agenda.controller.mapper;

import com.fabio.agenda.controller.request.AgendaItemRequest;
import com.fabio.agenda.controller.response.AgendaItemResponse;
import com.fabio.agenda.controller.response.VoteCountingResponse;
import com.fabio.agenda.repository.entity.AgendaItemEntity;
import com.fabio.agenda.repository.projection.VoteCounting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface AgendaItemInputMapper {

    AgendaItemEntity toEntity(AgendaItemRequest agendaItemRequest);

    @Mapping(source = "sessionOpenTime", target = "sessionOpenTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "sessionCloseTime", target = "sessionCloseTime", qualifiedByName = "localDateTimeToString")
    AgendaItemResponse toResponse(AgendaItemEntity agendaItemEntity);

    @Mapping(source = "sessionOpenTime", target = "sessionOpenTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "sessionCloseTime", target = "sessionCloseTime", qualifiedByName = "localDateTimeToString")
    VoteCountingResponse toResponse(VoteCounting voteCounting);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
