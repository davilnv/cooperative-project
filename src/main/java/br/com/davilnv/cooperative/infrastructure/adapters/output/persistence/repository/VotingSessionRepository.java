package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository;

import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSessionEntity, UUID> {
}
