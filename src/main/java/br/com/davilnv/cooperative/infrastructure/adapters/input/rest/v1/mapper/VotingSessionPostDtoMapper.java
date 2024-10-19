package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VotingSessionPostDto;

import java.time.LocalDateTime;

public class VotingSessionPostDtoMapper {
    public static VotingSession toDomain(VotingSessionPostDto votingSessionPostDto) {
        return new VotingSession(
                null,
                LocalDateTime.now(),
                votingSessionPostDto.closeDateTime() != null ? votingSessionPostDto.closeDateTime() : LocalDateTime.now().plusMinutes(1),
                new Agenda(votingSessionPostDto.agendaId())
        );
    }
}
