package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.enums.MemberVote;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Test
    void save_ShouldPersistVotingSession_WhenValid() {
        // Arrange
        VotingSession votingSession = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );

        // Act
        VotingSession savedVotingSession = votingSessionPersistenceAdapter.save(votingSession);

        // Assert
        assertNotNull(savedVotingSession);
        assertNotNull(savedVotingSession.getId());

    }

    @Test
    void save_ShouldPersistVotingSession_WhenVoteListIsNotEmpty() throws MemberAlreadyExistsException {
        // Arrange
        Agenda savedAgenda = agendaPersistenceAdapter.save(new Agenda(
                null,
                "Teste",
                "Teste Descricao",
                AgendaStatus.CREATED,
                TimeUtils.getDateTimeNow(),
                null
        ));

        Member memberSaved = memberPersistenceAdapter.save(new Member(
                null,
                "12345678901",
                "Jose da Silva",
                TimeUtils.getLocalDateFromString("10/10/2000")
        ));

        VotingSession votingSession = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );

        Vote vote = new Vote(
                savedAgenda,
                memberSaved,
                MemberVote.YES,
                TimeUtils.getDateTimeNow()
        );

        VotingSession savedVotingSession = votingSessionPersistenceAdapter.save(votingSession);
        savedVotingSession.setVotes(List.of(vote));

        // Act
        VotingSession updatedVotingSession = votingSessionPersistenceAdapter.save(votingSession);

        // Assert
        assertNotNull(updatedVotingSession);
        assertNotNull(updatedVotingSession.getId());
        assertEquals(1, updatedVotingSession.getVotes().size());
        assertEquals(vote, updatedVotingSession.getVotes().get(0));
    }

    @Test
    void findById_ShouldReturnVotingSession_WhenValid() throws NotFoundVotingSessionException {
        // Arrange
        VotingSession votingSession = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
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
        VotingSession votingSession = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );

        votingSessionPersistenceAdapter.save(votingSession);
        UUID notValidId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NotFoundVotingSessionException.class, () -> votingSessionPersistenceAdapter.findById(notValidId));
    }

    @Test
    void findAll_ShouldReturnAllVotingSessions_WhenValid() {
        // Arrange
        VotingSession votingSession1 = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );


        VotingSession votingSession2 = new VotingSession(
                null,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );

        votingSessionPersistenceAdapter.save(votingSession1);
        votingSessionPersistenceAdapter.save(votingSession2);

        // Act
        List<VotingSession> votingSessions = votingSessionPersistenceAdapter.findAll();

        // Assert
        assertNotNull(votingSessions);
        assertFalse(votingSessions.isEmpty());
        assertEquals(6, votingSessions.size());
    }

}
