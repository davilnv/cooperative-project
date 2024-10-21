package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.enums.MemberVote;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberVotePostDto;

public class MemberVotePostDtoMapper {
    public static Vote toDomain(MemberVotePostDto dto) {
        return new Vote(
                new Agenda(dto.agendaId()),
                new Member(dto.memberId()),
                MemberVote.fromCode(dto.vote()),
                TimeUtils.getDateTimeNow()
        );
    }
}
