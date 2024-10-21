package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import java.util.UUID;

public record MemberGetDto(
        UUID id,
        String cpf,
        String memberName,
        String birthDate
) {
}
