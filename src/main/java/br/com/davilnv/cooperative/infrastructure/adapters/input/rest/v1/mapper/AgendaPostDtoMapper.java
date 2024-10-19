package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;

import java.time.LocalDateTime;

public class AgendaPostDtoMapper {
    public static Agenda toDomain(AgendaPostDto agendaPostDto) {
        return new Agenda(
                null,
                agendaPostDto.title(),
                agendaPostDto.description(),
                AgendaStatus.CREATED,
                agendaPostDto.startDateTime() != null ? agendaPostDto.startDateTime() : LocalDateTime.now(),
                agendaPostDto.endDateTime()
        );
    }
}
