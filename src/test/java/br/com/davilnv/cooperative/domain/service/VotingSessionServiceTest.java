package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VotingSessionServiceTest {

    @Mock
    private VotingSessionOutputPort votingSessionOutputPort;

    @Mock
    private AgendaOutputPort agendaOutputPort;

    @InjectMocks
    private VotingSessionService votingSessionService;

    private VotingSession votingSession;
    private Agenda agenda;
    private UUID votingSessionId;
    private UUID agendaId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        votingSessionId = UUID.randomUUID();
        agendaId = UUID.randomUUID();

        agenda = new Agenda(
                agendaId,
                "Test Agenda",
                "Test Description",
                AgendaStatus.CREATED,
                TimeUtils.getDateTimeNow(),
                null
        );

        votingSession = new VotingSession(
                votingSessionId,
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );

        agenda.setVotingSession(votingSession);
    }

    @Test
    void createVotingSession_ShouldThrowRequiredAgendaException_WhenAgendaIdIsNull() {
        /// Act & Assert
        assertThrows(RequiredAgendaException.class, () -> votingSessionService.createVotingSession(null, null));
    }

    @Test
    void createVotingSession_ShouldReturnSavedVotingSession() throws NotFoundAgendaException, RequiredAgendaException, TheresAlreadyOpenVotingSessionException {
        // Arrange
        agenda.setVotingSession(null);
        when(agendaOutputPort.findById(agendaId)).thenReturn(agenda);
        when(votingSessionOutputPort.save(any(VotingSession.class))).thenReturn(votingSession);
        when(agendaOutputPort.save(agenda)).thenReturn(agenda);

        // Act
        VotingSession savedVotingSession = votingSessionService.createVotingSession(agendaId, votingSession.getCloseDateTime());

        // Assert
        assertNotNull(savedVotingSession);
        assertEquals(votingSession.getId(), savedVotingSession.getId());
        assertEquals(votingSession.getOpenDateTime(), savedVotingSession.getOpenDateTime());

        verify(agendaOutputPort, times(1)).findById(agendaId);
        verify(votingSessionOutputPort, times(1)).save(any(VotingSession.class));
        verify(agendaOutputPort, times(1)).save(agenda);
    }

    @Test
    void createVotingSession_ShouldThrowTheresAlreadyOpenVotingSessionException_WhenVotingSessionAlreadyExists() throws NotFoundAgendaException {
        // Arrange
        when(agendaOutputPort.findById(agendaId)).thenReturn(agenda);

        // Act & Assert
        assertThrows(TheresAlreadyOpenVotingSessionException.class, () -> votingSessionService.createVotingSession(agendaId, votingSession.getCloseDateTime()));

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

    @Test
    void getVotingSession_ShouldReturnVotingSession_WhenFound() throws NotFoundVotingSessionException {
        // Arrange
        when(votingSessionOutputPort.findById(votingSessionId)).thenReturn(votingSession);

        // Act
        VotingSession foundVotingSession = votingSessionService.getVotingSession(votingSessionId);

        // Assert
        assertNotNull(foundVotingSession);
        assertEquals(votingSession.getId(), foundVotingSession.getId());

        verify(votingSessionOutputPort, times(1)).findById(votingSessionId);
    }

    @Test
    void getVotingSession_ShouldThrowNotFoundVotingSessionException_WhenNotFound() throws NotFoundVotingSessionException {
        // Arrange
        when(votingSessionOutputPort.findById(votingSessionId)).thenThrow(new NotFoundVotingSessionException("Sessão de votação não aberta para a pauta de ID: " + votingSessionId));

        // Act & Assert
        assertThrows(NotFoundVotingSessionException.class, () -> votingSessionService.getVotingSession(votingSessionId));

        verify(votingSessionOutputPort, times(1)).findById(votingSessionId);
    }

    @Test
    void getVotingSessionByAgendaId_ShouldReturnVotingSession_WhenFound() throws NotFoundVotingSessionException, NotFoundAgendaException {
        // Arrange
        when(agendaOutputPort.findById(agendaId)).thenReturn(agenda);

        // Act
        VotingSession foundVotingSession = votingSessionService.getVotingSessionByAgendaId(agendaId);

        // Assert
        assertNotNull(foundVotingSession);
        assertEquals(votingSession.getId(), foundVotingSession.getId());

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

    @Test
    void getVotingSessionByAgendaId_ShouldThrowNotFoundVotingSessionException_WhenNotFound() throws NotFoundAgendaException {
        // Arrange
        agenda.setVotingSession(null);
        when(agendaOutputPort.findById(agendaId)).thenReturn(agenda);

        // Act & Assert
        assertThrows(NotFoundVotingSessionException.class, () -> votingSessionService.getVotingSessionByAgendaId(agendaId));

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

    @Test
    void getVotingSessionByAgendaId_ShouldThrowNotFoundAgendaException_WhenNotFound() throws NotFoundAgendaException {
        // Arrange
        when(agendaOutputPort.findById(agendaId)).thenThrow(new NotFoundAgendaException("Pauta não encontrada para o ID: " + agendaId));

        // Act & Assert
        assertThrows(NotFoundAgendaException.class, () -> votingSessionService.getVotingSessionByAgendaId(agendaId));

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

}
