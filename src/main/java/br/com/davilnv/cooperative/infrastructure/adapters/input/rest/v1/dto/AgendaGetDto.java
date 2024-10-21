package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendaGetDto(
        UUID id,
        String title,
        String description,
        AgendaStatus status,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime startDateTime,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime endDateTime,

        VotingSessionGetDto votingSession,
        int votesYes,
        int votesNo
) {
}
