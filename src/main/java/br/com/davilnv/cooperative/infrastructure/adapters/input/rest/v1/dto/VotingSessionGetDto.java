package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import java.util.List;
import java.util.UUID;

public record VotingSessionGetDto(
        UUID id,
        String openDateTime,
        String closeDateTime,
        List<VoteGetDto> votes
) {
}
