package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.*;

public class AgendaServiceTest {

    @Mock
    private AgendaOutputPort agendaOutputPort;

    @InjectMocks
    private AgendaService agendaService;

    private Agenda agenda;
    private UUID agendaId;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);
        agendaId = UUID.randomUUID();
        agenda = new Agenda(
                agendaId,
                "Test Agenda",
                "Test Description",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );
    }


    @Test
    void createAgenda_ShouldReturnSavedAgenda() {
        // Arrange
        when(agendaOutputPort.save(agenda)).thenReturn(agenda);

        // Act
        Agenda savedAgenda = agendaService.createAgenda(agenda);

        // Assert
        assertNotNull(savedAgenda);
        assertEquals(agenda.getId(), savedAgenda.getId());
        assertEquals(agenda.getTitle(), savedAgenda.getTitle());

        verify(agendaOutputPort, times(1)).save(agenda);
    }

    @Test
    void createAgenda_ShouldReturnSavedAgenda_WhenVotingSessionIsNotNull() {
        // Arrange
        UUID votingSessionId = UUID.randomUUID();
        VotingSession votingSession = new VotingSession(votingSessionId, LocalDateTime.now(), null, agenda);
        agenda.setVotingSession(votingSession);
        when(agendaOutputPort.save(agenda)).thenReturn(agenda);

        // Act
        Agenda savedAgenda = agendaService.createAgenda(agenda);

        // Assert
        assertNotNull(savedAgenda);
        assertNotNull(savedAgenda.getVotingSession());
        assertNotNull(savedAgenda.getVotingSession().getAgenda());
        assertEquals(agenda.getId(), savedAgenda.getId());
        assertEquals(agenda.getTitle(), savedAgenda.getTitle());
        assertEquals(votingSessionId, savedAgenda.getVotingSession().getId());

        verify(agendaOutputPort, times(1)).save(agenda);
    }

    @Test
    void getAgenda_ShouldReturnAgenda_WhenFound() throws NotFoundAgendaException {
        // Arrange
        when(agendaOutputPort.findById(agendaId)).thenReturn(agenda);

        // Act
        Agenda foundAgenda = agendaService.getAgenda(agendaId);

        // Assert
        assertNotNull(foundAgenda);
        assertEquals(agenda.getId(), foundAgenda.getId());

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

    @Test
    void getAgenda_ShouldThrowNotFoundAgendaException_WhenNotFound() throws NotFoundAgendaException {
        // Arrange
        when(agendaOutputPort.findById(agendaId)).thenThrow(new NotFoundAgendaException("Agenda não encontrada para o ID: " + agendaId));

        // Act & Assert
        assertThrows(NotFoundAgendaException.class, () -> agendaService.getAgenda(agendaId));

        verify(agendaOutputPort, times(1)).findById(agendaId);
    }

    @Test
    void getAllAgendas_ShouldReturnListOfAgendas() {
        // Arrange
        List<Agenda> agendaList = List.of(agenda);
        when(agendaOutputPort.findAll()).thenReturn(agendaList);

        // Act
        List<Agenda> result = agendaService.getAllAgendas();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(agendaOutputPort, times(1)).findAll();
    }

}
