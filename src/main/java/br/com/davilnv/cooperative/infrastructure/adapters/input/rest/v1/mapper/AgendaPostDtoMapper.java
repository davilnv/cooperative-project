package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;

public class AgendaPostDtoMapper {
    public static AgendaPostDto toDto(Agenda agenda) {
        return new AgendaPostDto(
                agenda.getTitle(),
                agenda.getDescription(),
                agenda.getStatus(),
                agenda.getStartDateTime(),
                agenda.getEndDateTime()
        );
    }

    public static Agenda toDomain(AgendaPostDto agendaPostDto) {
        return new Agenda(
                null,
                agendaPostDto.title(),
                agendaPostDto.description(),
                agendaPostDto.status(),
                agendaPostDto.startDateTime(),
                agendaPostDto.endDateTime()
        );
    }
}
