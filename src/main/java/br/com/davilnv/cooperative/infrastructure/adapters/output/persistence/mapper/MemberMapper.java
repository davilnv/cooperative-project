package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.MemberEntity;

public class MemberMapper {

    public static MemberEntity toEntity(Member member) {
        return new MemberEntity(
                member.getId(),
                member.getCpf(),
                member.getMemberName(),
                member.getBirthDate()
        );
    }

    public static Member toDomain(MemberEntity memberEntity) {
        return new Member(
                memberEntity.getId(),
                memberEntity.getCpf(),
                memberEntity.getMemberName(),
                memberEntity.getBirthDate()
        );
    }
}
