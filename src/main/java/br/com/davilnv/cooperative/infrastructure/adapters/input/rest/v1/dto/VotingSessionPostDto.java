package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record VotingSessionPostDto(

        @NotNull(message = "O id da pauta é obrigatório")
        UUID agendaId,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime closeDateTime

) {
}
