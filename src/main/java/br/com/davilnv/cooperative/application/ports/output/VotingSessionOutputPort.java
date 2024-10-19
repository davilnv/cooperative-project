package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.model.VotingSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotingSessionOutputPort {

    VotingSession save(VotingSession votingSession);

    Optional<VotingSession> findById(UUID votingSessionId);

    List<VotingSession> findAll();

}
