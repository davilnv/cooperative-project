package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateVotingSessionUseCase;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebMvcTest(VotingSessionRestAdapter.class)
public class VotingSessionRestAdapterTest extends BaseRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateVotingSessionUseCase createVotingSessionUseCase;

    private VotingSession votingSession;
    private Agenda agenda;

    @BeforeEach
    public void setUp() throws IOException {
        baseEndpoint = API_BASE_V1 + "voting-session";
        agenda = new Agenda(
                UUID.fromString("be038a4b-f0b0-4bd7-8ea0-b36e93c9d917"),
                "Teste",
                "Teste Descrição",
                AgendaStatus.CREATED,
                LocalDateTime.now(),
                null
        );

        votingSession = new VotingSession(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                agenda
        );

        json = getJson("voting-session");
    }

    @Test
    void openVotingSession_ShouldReturnCreated_WhenVotingSessionIsValid() throws Exception {
        // Arrange
        when(createVotingSessionUseCase.createVotingSession(any(VotingSession.class))).thenReturn(votingSession);

        // Act & Assert
        mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(votingSession.getId().toString()))
                .andExpect(jsonPath("$.openDateTime").isNotEmpty())
                .andExpect(jsonPath("$.closeDateTime").isNotEmpty());
    }

    @Test
    void openVotingSession_ShouldThrowRequiredAgendaException_WhenAgendaIsNull() throws Exception {
        // Arrange
        String errorMessage = "Pauta é obrigatória para criar uma sessão de votação";
        when(createVotingSessionUseCase.createVotingSession(any(VotingSession.class)))
                .thenThrow(new RequiredAgendaException(errorMessage));
        json = getJson("null-voting-session");

        // Act & Assert
        String response = mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        assertEquals(errorMessage, response);
    }

    @Test
    void openVotingSession_ShouldThrowNotFoundAgendaException_WhenAgendaNonExistent() throws Exception {
        // Arrange
        String errorMessage = "Agenda não encontrada para o ID: ";
        when(createVotingSessionUseCase.createVotingSession(any(VotingSession.class)))
                .thenThrow(new NotFoundAgendaException(errorMessage));
        json = getJson("non-existent-voting-session");

        // Act & Assert
        String response = mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(errorMessage));
    }

    @Test
    void openVotingSession_ShouldThrowTheresAlreadyOpenVotingSessionException_WhenVotingSessionAlreadyOpen() throws Exception {
        // Arrange
        String errorMessage = "Já existe uma sessão de votação para a pauta de ID: ";
        when(createVotingSessionUseCase.createVotingSession(any(VotingSession.class)))
                .thenThrow(new TheresAlreadyOpenVotingSessionException(errorMessage));
        json = getJson("voting-session");

        // Act & Assert
        String response = mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(errorMessage));
    }

}