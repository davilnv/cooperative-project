package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;

import java.util.List;
import java.util.UUID;

public interface VotingSessionOutputPort {

    VotingSession save(VotingSession votingSession);

    Vote saveVote(Vote vote);

    VotingSession findById(UUID votingSessionId) throws NotFoundVotingSessionException;

    List<VotingSession> findAll();

}
