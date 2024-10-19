package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AgendaPostDto(

        @NotBlank(message = "O título da pauta é obrigatório")
        @Size(max = 100, message = "O título da pauta deve ter no máximo 60 caracteres")
        String title,

        @Size(max = 255, message = "A descrição da pauta deve ter no máximo 255 caracteres")
        String description,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime startDateTime,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime endDateTime
) {
}
