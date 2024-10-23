package br.com.davilnv.cooperative.infrastructure.adapters.output.client.dto;

public record UserStatusResponse(
        String status,
        String cpf,
        String id
) {
}
