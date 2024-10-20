package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateMemberUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetMemberUseCase;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberRestAdapter.class)
public class MemberRestAdapterTest extends BaseRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateMemberUseCase createMemberUseCase;

    @MockBean
    private GetMemberUseCase getMemberUseCase;

    private Member member;

    @BeforeEach
    public void setUp() throws IOException {
        // Arrange
        baseEndpoint = API_BASE_V1 + "member";
        member = new Member(
                UUID.randomUUID(),
                "81269707078",
                "João José",
                LocalDate.of(2000, 10, 10)
        );

        // Load JSON file
        json = getJson("member");
    }

    @Test
    void createMember_ShouldReturnCreated_WhenMemberIsValid() throws Exception {
        // Arrange
        when(createMemberUseCase.createMember(any(Member.class))).thenReturn(member);

        // Act & Assert
        mockMvc.perform(post(baseEndpoint)
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(member.getId().toString()))
                .andExpect(jsonPath("$.cpf").value(member.getCpf()));
    }

    @Test
    void createMember_ShouldMemberAlreadyExistsException_WhenMemberNotExists() throws Exception {
        // Arrange
        when(createMemberUseCase.createMember(any(Member.class))).thenThrow(new MemberAlreadyExistsException("Associado já cadastrado para o CPF: "));

        // Act & Assert
        mockMvc.perform(post(baseEndpoint)
                        .contentType(MEDIA_TYPE)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void getMember_ShouldReturnMember_WhenMemberExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getMember(any(UUID.class))).thenReturn(member);

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/" + member.getId())
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId().toString()))
                .andExpect(jsonPath("$.cpf").value(member.getCpf()));
    }

    @Test
    void getMember_ShouldReturnNotFoundException_WhenMemberNotExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getMember(any(UUID.class))).thenThrow(new NotFoundMemberException("Associado não encontrado para o ID: "));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/" + UUID.randomUUID())
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMemberByCpf_ShouldReturnMember_WhenMemberExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getMemberByCpf(any(String.class))).thenReturn(member);

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/cpf/" + member.getCpf())
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId().toString()))
                .andExpect(jsonPath("$.cpf").value(member.getCpf()));
    }

    @Test
    void getMemberByCpf_ShouldReturnNotFoundException_WhenMemberNotExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getMemberByCpf(any(String.class))).thenThrow(new NotFoundMemberException("Associado não encontrado para o CPF: "));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint + "/cpf/41811165060")
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllMembers_ShouldReturnMembers_WhenMembersExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getAllMembers()).thenReturn(java.util.List.of(member));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint)
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(member.getId().toString()))
                .andExpect(jsonPath("$[0].cpf").value(member.getCpf()));
    }

    @Test
    void getAllMembers_ShouldReturnNotFound_WhenMembersNotExists() throws Exception {
        // Arrange
        when(getMemberUseCase.getAllMembers()).thenThrow(new NotFoundMemberException("Nenhum associado encontrado"));

        // Act & Assert
        mockMvc.perform(get(baseEndpoint)
                        .contentType(MEDIA_TYPE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("[]"));
    }

}
