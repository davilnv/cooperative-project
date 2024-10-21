package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VotingSessionGetDto(
        UUID id,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime openDateTime,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime closeDateTime,

        List<VoteGetDto> votes
) {
}
