package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import java.util.UUID;

public record AgendaGetDto(
        UUID id,
        String title,
        String description,
        AgendaStatus status,
        String startDateTime,
        String endDateTime,
        VotingSessionGetDto votingSession,
        int votesYes,
        int votesNo
) {
}
