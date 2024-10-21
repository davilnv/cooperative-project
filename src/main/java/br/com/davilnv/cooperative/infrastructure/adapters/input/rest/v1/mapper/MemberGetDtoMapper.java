package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberGetDto;

public class MemberGetDtoMapper {
    public static MemberGetDto toDto(Member member) {
        return new MemberGetDto(
                member.getId(),
                member.getCpf(),
                member.getMemberName(),
                TimeUtils.getStringFromLocalDate(member.getBirthDate())
        );
    }
}
