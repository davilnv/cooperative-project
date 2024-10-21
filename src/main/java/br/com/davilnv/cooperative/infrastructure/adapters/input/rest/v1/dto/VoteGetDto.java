package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto;

import br.com.davilnv.cooperative.domain.enums.MemberVote;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record VoteGetDto(
        String member,
        MemberVote memberVote,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime voteDateTime
) {
}
