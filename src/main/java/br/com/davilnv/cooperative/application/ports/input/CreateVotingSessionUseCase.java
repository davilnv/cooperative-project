package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;

public interface CreateVotingSessionUseCase {

    VotingSession createVotingSession(VotingSession votingSession) throws RequiredAgendaException, NotFoundAgendaException, TheresAlreadyOpenVotingSessionException;

}
