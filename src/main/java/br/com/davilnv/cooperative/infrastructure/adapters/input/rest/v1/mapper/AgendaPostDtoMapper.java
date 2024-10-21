package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;

public class AgendaPostDtoMapper {
    public static Agenda toDomain(AgendaPostDto agendaPostDto) {
        return new Agenda(
                null,
                agendaPostDto.title(),
                agendaPostDto.description(),
                AgendaStatus.CREATED,
                agendaPostDto.startDateTime() != null ? agendaPostDto.startDateTime() : TimeUtils.getDateTimeNow(),
                agendaPostDto.endDateTime()
        );
    }
}
