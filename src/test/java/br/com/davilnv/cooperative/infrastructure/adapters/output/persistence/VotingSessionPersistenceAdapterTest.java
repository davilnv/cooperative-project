package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class VotingSessionPersistenceAdapterTest {

    @Autowired
    private VotingSessionPersistenceAdapter votingSessionPersistenceAdapter;

    @Autowired
    private AgendaPersistenceAdapter agendaPersistenceAdapter;

    @Test
    void save_ShouldPersistVotingSession_WhenValid() {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Test Agenda",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda savedAgenda = agendaPersistenceAdapter.save(agenda);

        VotingSession votingSession = new VotingSession(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                savedAgenda
        );

        // Act
        VotingSession savedVotingSession = votingSessionPersistenceAdapter.save(votingSession);

        // Assert
        assertNotNull(savedVotingSession);
        assertNotNull(savedVotingSession.getId());

    }

    @Test
    void findById_ShouldReturnVotingSession_WhenValid() throws NotFoundVotingSessionException {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Test Agenda2",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda savedAgenda = agendaPersistenceAdapter.save(agenda);

        VotingSession votingSession = new VotingSession(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                savedAgenda
        );

        VotingSession savedVotingSession = votingSessionPersistenceAdapter.save(votingSession);

        // Act
        VotingSession foundVotingSession = votingSessionPersistenceAdapter.findById(savedVotingSession.getId());

        // Assert
        assertNotNull(foundVotingSession);
        assertEquals(savedVotingSession, foundVotingSession);
    }

    @Test
    void findById_ShouldThrowNotFoundVotingSessionException_WhenNotValid() {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Test Agenda2",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda savedAgenda = agendaPersistenceAdapter.save(agenda);

        VotingSession votingSession = new VotingSession(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                savedAgenda
        );

        votingSessionPersistenceAdapter.save(votingSession);
        UUID notValidId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NotFoundVotingSessionException.class, () -> votingSessionPersistenceAdapter.findById(notValidId));
    }

    @Test
    void findAll_ShouldReturnAllVotingSessions_WhenValid() {
        // Arrange
        Agenda agenda1 = new Agenda(
                null,
                "Test Agenda3",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda agenda2 = new Agenda(
                null,
                "Test Agenda4",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda savedAgenda1 = agendaPersistenceAdapter.save(agenda1);
        Agenda savedAgenda2 = agendaPersistenceAdapter.save(agenda2);

        VotingSession votingSession1 = new VotingSession(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                savedAgenda1
        );


        VotingSession votingSession2 = new VotingSession(
                null,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                savedAgenda2
        );

        votingSessionPersistenceAdapter.save(votingSession1);
        votingSessionPersistenceAdapter.save(votingSession2);

        // Act
        List<VotingSession> votingSessions = votingSessionPersistenceAdapter.findAll();

        // Assert
        assertNotNull(votingSessions);
        assertFalse(votingSessions.isEmpty());
        assertEquals(5, votingSessions.size());
    }

}
