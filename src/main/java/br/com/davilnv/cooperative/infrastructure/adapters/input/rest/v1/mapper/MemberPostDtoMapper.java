package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberPostDto;

public class MemberPostDtoMapper {
    public static Member toDomain(MemberPostDto memberPostDto) {
        return new Member(
                null,
                memberPostDto.cpf(),
                memberPostDto.memberName(),
                memberPostDto.birthDate()
        );
    }
}
