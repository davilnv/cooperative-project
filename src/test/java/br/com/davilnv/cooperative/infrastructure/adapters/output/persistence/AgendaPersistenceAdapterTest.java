package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
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
public class AgendaPersistenceAdapterTest {

    @Autowired
    private AgendaPersistenceAdapter agendaPersistenceAdapter;

    @Test
    void save_ShouldPersistAgenda_WhenValid() {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Teste",
                "Teste de descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        // Act
        Agenda savedAgenda = agendaPersistenceAdapter.save(agenda);

        // Assert
        assertNotNull(savedAgenda);
        assertNotNull(savedAgenda.getId());
        assertEquals(agenda.getTitle(), savedAgenda.getTitle());

    }

    @Test
    void findById_ShouldReturnAgenda_WhenValid() throws NotFoundAgendaException {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Teste2",
                "Teste de descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda savedAgenda = agendaPersistenceAdapter.save(agenda);

        // Act
        Agenda foundAgenda = agendaPersistenceAdapter.findById(savedAgenda.getId());

        // Assert
        assertNotNull(foundAgenda);
        assertEquals(savedAgenda, foundAgenda);
        assertEquals(savedAgenda.getTitle(), foundAgenda.getTitle());
    }

    @Test
    void findById_ShouldThrowNotFoundAgendaException_WhenNotValid() {
        // Arrange
        Agenda agenda = new Agenda(
                null,
                "Teste2",
                "Teste de descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        agendaPersistenceAdapter.save(agenda);
        UUID notValidId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NotFoundAgendaException.class, () -> agendaPersistenceAdapter.findById(notValidId));
    }

    @Test
    void findAll_ShouldReturnAllAgendas_WhenValid() throws NotFoundAgendaException {
        // Arrange
        Agenda agenda1 = new Agenda(
                null,
                "Teste3",
                "Teste de descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        Agenda agenda2 = new Agenda(
                null,
                "Teste4",
                "Teste de descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        agendaPersistenceAdapter.save(agenda1);
        agendaPersistenceAdapter.save(agenda2);

        // Act
        List<Agenda> agendas = agendaPersistenceAdapter.findAll();

        // Assert
        assertNotNull(agendas);
        assertFalse(agendas.isEmpty());
        assertEquals(4, agendas.size());
    }

}
