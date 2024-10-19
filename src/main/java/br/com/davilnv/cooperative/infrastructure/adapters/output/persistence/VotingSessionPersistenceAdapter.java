package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.VotingSessionMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.VotingSessionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VotingSessionPersistenceAdapter implements VotingSessionOutputPort {

    private final VotingSessionRepository votingSessionRepository;

    public VotingSessionPersistenceAdapter(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    @Override
    public VotingSession save(VotingSession votingSession) {
        VotingSessionEntity votingSessionEntity = VotingSessionMapper.toEntity(votingSession);
        return VotingSessionMapper.toDomain(votingSessionRepository.save(votingSessionEntity));
    }

    @Override
    public Optional<VotingSession> findById(UUID votingSessionId) {
        return votingSessionRepository.findById(votingSessionId)
                .map(VotingSessionMapper::toDomain);
    }

    @Override
    public List<VotingSession> findAll() {
        return votingSessionRepository.findAll()
                .stream()
                .map(VotingSessionMapper::toDomain)
                .toList();
    }
}
