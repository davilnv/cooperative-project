package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MemberVotePostDto(

        @NotNull(message = "ID da pauta é obrigatório")
        UUID agendaId,

        @NotNull(message = "ID do membro é obrigatório")
        UUID memberId,

        @NotNull(message = "Voto do membro é obrigatório")
        @Size(min = 1, max = 1, message = "Voto do membro deve ter 1 caractere")
        @Pattern(regexp = "^([YN])$", message = "Voto do membro deve ser SIM (Y) ou NÃO (N)")
        String vote
) {
}
