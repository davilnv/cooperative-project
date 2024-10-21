package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VoteGetDto;

public class VoteGetDtoMapper {
    public static VoteGetDto toDto(Vote vote) {
        return new VoteGetDto(
                vote.getMember().getMemberName(),
                vote.getMemberVote(),
                vote.getVoteDateTime() != null
                        ? TimeUtils.getStringFromLocalDateTime(vote.getVoteDateTime()) : null
        );
    }
}
