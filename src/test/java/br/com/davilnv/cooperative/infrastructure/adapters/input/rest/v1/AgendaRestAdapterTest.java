package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateAgendaUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetAgendaUseCase;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.UUID;

@WebMvcTest(AgendaRestAdapter.class)
public class AgendaRestAdapterTest extends BaseRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAgendaUseCase createAgendaUseCase;

    @MockBean
    private GetAgendaUseCase getAgendaUseCase;

    private Agenda agenda;

    @BeforeEach
    public void setUp() throws IOException {
        // Arrange
        baseEndpoint = API_BASE_V1 + "agenda";
        agenda = new Agenda(
                UUID.randomUUID(),
                "Teste",
                "Teste Descrição",
                AgendaStatus.CREATED,
                TimeUtils.getDateTimeNow(),
                null
        );

        // Load JSON file
        json = getJson("agenda");
    }

    @Test
    void createAgenda_ShouldReturnCreated_WhenAgendaIsValid() throws Exception {
        // Arrange
        when(createAgendaUseCase.createAgenda(any(Agenda.class))).thenReturn(agenda);

        // Act & Assert
        mockMvc.perform(post(baseEndpoint)
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(agenda.getId().toString()))
                .andExpect(jsonPath("$.status").value(agenda.getStatus().toString()));
    }

    @Test
    void getAgenda_ShouldReturnAgenda_WhenAgendaExists() throws Exception {
        // Arrange
        when(getAgendaUseCase.getAgenda(any(UUID.class))).thenReturn(agenda);

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/" + agenda.getId())
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(agenda.getId().toString()))
                .andExpect(jsonPath("$.title").value(agenda.getTitle()));
    }

    @Test
    void getAgenda_ShouldReturnNotFound_WhenAgendaNotExists() throws Exception {
        // Arrange
        when(getAgendaUseCase.getAgenda(any(UUID.class))).thenThrow(new NotFoundAgendaException("Nenhuma agenda encontrada"));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/" + UUID.randomUUID())
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAgendas_ShouldReturnAgendas_WhenAgendasExists() throws Exception {
        // Arrange
        when(getAgendaUseCase.getAllAgendas()).thenReturn(java.util.List.of(agenda));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint)
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(agenda.getId().toString()))
                .andExpect(jsonPath("$[0].title").value(agenda.getTitle()));
    }

    @Test
    void getAllAgendas_ShouldReturnNotFound_WhenAgendasNotExists() throws Exception {
        // Arrange
        when(getAgendaUseCase.getAllAgendas()).thenThrow(new NotFoundAgendaException("Nenhuma agenda encontrada"));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint)
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("[]"));
    }

}
