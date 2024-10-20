package br.com.davilnv.cooperative.domain.model;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AgendaTest {

    @Test
    void canOpenVotingSession_ShouldReturnTrue_WhenStatusIsCreatedAndVotingSessionIsNull() {
        // Arrange
        Agenda agenda = new Agenda();
        agenda.setStatus(AgendaStatus.CREATED);

        // Act
        boolean result = agenda.canOpenVotingSession();

        // Assert
        assertTrue(result);
    }

    @Test
    void canOpenVotingSession_ShouldReturnFalse_WhenStatusIsNotCreated() {
        for (AgendaStatus agendaStatus : AgendaStatus.values()) {
            if (agendaStatus.equals(AgendaStatus.CREATED)) {
                continue;
            }

            // Arrange
            Agenda agenda = new Agenda();
            agenda.setStatus(agendaStatus);

            // Act
            boolean result = agenda.canOpenVotingSession();

            // Assert
            assertFalse(result);
        }
    }

    @Test
    void canOpenVotingSession_ShouldReturnFalse_WhenVotingSessionExists() {
        // Arrange
        Agenda agenda = new Agenda();
        agenda.setStatus(AgendaStatus.CREATED);
        VotingSession votingSession = new VotingSession();
        agenda.setVotingSession(votingSession);

        // Act
        boolean result = agenda.canOpenVotingSession();

        // Assert
        assertFalse(result);
    }

}
