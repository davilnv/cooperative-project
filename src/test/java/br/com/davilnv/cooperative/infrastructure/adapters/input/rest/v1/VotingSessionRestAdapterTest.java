package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.input.PerformVoteUseCase;
import br.com.davilnv.cooperative.application.ports.output.MemberOutputPort;
import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private PerformVoteUseCase performVoteUseCase;

    @MockBean
    private VotingSessionOutputPort votingSessionOutputPort;

    @MockBean
    private MemberOutputPort memberOutputPort;

    private VotingSession votingSession;
    private final String dateTime = "22/10/2024 22:22:00";
    private final UUID agendaId = UUID.randomUUID();

    @BeforeEach
    public void setUp() throws IOException {
        baseEndpoint = API_BASE_V1 + "voting-session";

        votingSession = new VotingSession(
                UUID.randomUUID(),
                TimeUtils.getDateTimeNow(),
                TimeUtils.getDateTimeNowPlusOneMinute()
        );
    }

    private String getJsonString(UUID agendaId, String closeDateTime) {
        return "{\"agendaId\": \"" + agendaId + "\", \"closeDateTime\": \"" + closeDateTime + "\"}";
    }

    @Test
    void openVotingSession_ShouldReturnCreated_WhenVotingSessionIsValid() throws Exception {
        // Arrange
        String json = getJsonString(agendaId, dateTime);
        when(createVotingSessionUseCase.createVotingSession(agendaId, TimeUtils.getLocalDateTimeFromString(dateTime))).thenReturn(votingSession);

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
        String errorMessage = "O id da pauta é obrigatório";
        when(createVotingSessionUseCase.createVotingSession(null, TimeUtils.getLocalDateTimeFromString(dateTime)))
                .thenThrow(new RequiredAgendaException(errorMessage));
        String json = "{\"agendaId\": null}";

        // Act & Assert
        mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .characterEncoding("UTF-8")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.agendaId").value(errorMessage));
    }

    @Test
    void openVotingSession_ShouldThrowNotFoundAgendaException_WhenAgendaNonExistent() throws Exception {
        // Arrange
        String errorMessage = "Agenda não encontrada para o ID: ";
        String json = getJsonString(agendaId, dateTime);
        when(createVotingSessionUseCase.createVotingSession(agendaId, TimeUtils.getLocalDateTimeFromString(dateTime)))
                .thenThrow(new NotFoundAgendaException(errorMessage));

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
        String json = getJsonString(agendaId, dateTime);
        when(createVotingSessionUseCase.createVotingSession(agendaId, TimeUtils.getLocalDateTimeFromString(dateTime)))
                .thenThrow(new TheresAlreadyOpenVotingSessionException(errorMessage));

        // Act & Assert
        String response = mockMvc.perform(post(baseEndpoint + "/open")
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(errorMessage));

        verify(createVotingSessionUseCase).createVotingSession(any(UUID.class), any(LocalDateTime.class));
    }

}