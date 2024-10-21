package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.utils.StaticsUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record VotingSessionPostDto(

        @NotNull(message = "O id da pauta é obrigatório")
        UUID agendaId,

        @Pattern(regexp = StaticsUtils.DATE_TIME_PATTERN, message = "A data de abertura da sessão deve estar no formato dd/MM/yyyy HH:mm:ss")
        String closeDateTime

) {
}
