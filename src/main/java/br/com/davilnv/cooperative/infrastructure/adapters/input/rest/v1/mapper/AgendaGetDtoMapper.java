package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaGetDto;

public class AgendaGetDtoMapper {
    public static AgendaGetDto toDto(Agenda agenda) {
        return new AgendaGetDto(
                agenda.getId(),
                agenda.getTitle(),
                agenda.getDescription(),
                agenda.getStatus(),
                agenda.getStartDateTime() != null ? TimeUtils.getStringFromLocalDateTime(agenda.getStartDateTime()) : null,
                agenda.getEndDateTime() != null ? TimeUtils.getStringFromLocalDateTime(agenda.getEndDateTime()) : null,
                agenda.getVotingSession() != null
                        ? VotingSessionGetDtoMapper.toDto(agenda.getVotingSession()) : null,
                agenda.getVotesYes(),
                agenda.getVotesNo()
        );
    }
}
