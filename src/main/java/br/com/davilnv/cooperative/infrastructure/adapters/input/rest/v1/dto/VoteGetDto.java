package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.enums.MemberVote;

public record VoteGetDto(
        String member,
        MemberVote memberVote,
        String voteDateTime
) {
}
