package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.utils.StaticsUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgendaPostDto(

        @NotBlank(message = "O título da pauta é obrigatório")
        @Size(max = 100, message = "O título da pauta deve ter no máximo 60 caracteres")
        String title,

        @Size(max = 255, message = "A descrição da pauta deve ter no máximo 255 caracteres")
        String description,

        @Pattern(regexp = StaticsUtils.DATE_TIME_PATTERN, message = "A data de início da pauta deve estar no formato dd/MM/yyyy HH:mm:ss")
        String startDateTime,

        @Pattern(regexp = StaticsUtils.DATE_TIME_PATTERN, message = "A data de início da pauta deve estar no formato dd/MM/yyyy HH:mm:ss")
        String endDateTime
) {
}
