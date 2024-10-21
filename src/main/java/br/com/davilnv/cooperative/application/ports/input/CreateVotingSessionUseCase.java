package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CreateVotingSessionUseCase {

    VotingSession createVotingSession(UUID agendaId, LocalDateTime closeDateTime)
            throws RequiredAgendaException, NotFoundAgendaException, TheresAlreadyOpenVotingSessionException;

}
