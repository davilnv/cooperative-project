package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record VotingSessionGetDto(
        UUID id,
        LocalDateTime openDateTime,
        LocalDateTime closeDateTime
) {
}
