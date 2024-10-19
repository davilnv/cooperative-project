package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper;

import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VotingSessionGetDto;

public class VotingSessionGetDtoMapper {
    public static VotingSessionGetDto toDto(VotingSession votingSession) {
        return new VotingSessionGetDto(
                votingSession.getId(),
                votingSession.getOpenDateTime(),
                votingSession.getCloseDateTime()
        );
    }
}
