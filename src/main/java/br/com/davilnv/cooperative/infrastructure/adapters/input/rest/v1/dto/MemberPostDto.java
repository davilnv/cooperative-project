package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberPostDto(

        @NotBlank(message = "O CPF do associado é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF do associado deve ter 11 caracteres")
        @Pattern(regexp = "^[0-9]*$", message = "O CPF do associado deve conter apenas números")
        String cpf,

        @NotBlank(message = "O nome do associado é obrigatório")
        @Size(min = 1, max = 150, message = "O nome do associado deve ter no máximo 150 caracteres")
        String memberName,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate

) {
}
