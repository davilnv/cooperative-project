package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VotingSessionGetDto;

public class VotingSessionGetDtoMapper {
    public static VotingSessionGetDto toDto(VotingSession votingSession) {
        return votingSession == null ? null : new VotingSessionGetDto(
                votingSession.getId(),
                votingSession.getOpenDateTime() != null ? TimeUtils.getStringFromLocalDateTime(votingSession.getOpenDateTime()) : null,
                votingSession.getCloseDateTime() != null ? TimeUtils.getStringFromLocalDateTime(votingSession.getCloseDateTime()) : null,
                votingSession.getVotes().stream().map(VoteGetDtoMapper::toDto).toList()
        );
    }
}
