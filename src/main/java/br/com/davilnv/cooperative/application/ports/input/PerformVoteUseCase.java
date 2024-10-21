package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.*;
import br.com.davilnv.cooperative.domain.model.Vote;

public interface PerformVoteUseCase {

    Vote performVote(Vote vote)
            throws NotFoundAgendaException, NotFoundVotingSessionException, NotFoundMemberException, MemberAlreadyVotedException, InvalidInformationVoteException;

}
