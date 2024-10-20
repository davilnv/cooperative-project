package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.output.MemberOutputPort;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberOutputPort memberOutputPort;

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private UUID memberId;
    private String cpf;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);
        memberId = UUID.randomUUID();
        cpf = "81269707078";
        member = new Member(
                memberId,
                cpf,
                "João José",
                LocalDate.of(2000, 10, 10)
        );
    }

    @Test
    void createMember_ShouldReturnSavedMember() throws MemberAlreadyExistsException {
        // Arrange
        when(memberOutputPort.save(member)).thenReturn(member);

        // Act
        Member savedMember = memberService.createMember(member);

        // Assert
        assertNotNull(savedMember);
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getCpf(), savedMember.getCpf());

        verify(memberOutputPort, times(1)).save(member);
    }

    @Test
    void createMember_ShouldThrowMemberAlreadyExistsException_WhenMemberAlreadyExists() throws MemberAlreadyExistsException {
        // Arrange
        when(memberOutputPort.save(member)).thenThrow(new MemberAlreadyExistsException("Associado já cadastrado para o CPF: " + member.getCpf()));

        // Act & Assert
        assertThrows(MemberAlreadyExistsException.class, () -> memberService.createMember(member));

        verify(memberOutputPort, times(1)).save(member);
    }

    @Test
    void getMember_ShouldReturnMember_WhenFound() throws NotFoundMemberException {
        // Arrange
        when(memberOutputPort.findById(memberId)).thenReturn(member);

        // Act
        Member foundMember = memberService.getMember(memberId);

        // Assert
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());

        verify(memberOutputPort, times(1)).findById(memberId);
    }

    @Test
    void getMember_ShouldThrowNotFoundMemberException_WhenNotFound() throws NotFoundMemberException {
        // Arrange
        when(memberOutputPort.findById(memberId)).thenThrow(new NotFoundMemberException("Associado não encontrada para o ID: " + memberId));

        // Act & Assert
        assertThrows(NotFoundMemberException.class, () -> memberService.getMember(memberId));

        verify(memberOutputPort, times(1)).findById(memberId);
    }

    @Test
    void getMemberByCpf_ShouldReturnMember_WhenFound() throws NotFoundMemberException {
        // Arrange
        when(memberOutputPort.findByCpf(cpf)).thenReturn(member);

        // Act
        Member foundMember = memberService.getMemberByCpf(cpf);

        // Assert
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());

        verify(memberOutputPort, times(1)).findByCpf(cpf);
    }

    @Test
    void getMemberByCpf_ShouldThrowNotFoundMemberException_WhenNotFound() throws NotFoundMemberException {
        // Arrange
        when(memberOutputPort.findByCpf(cpf)).thenThrow(new NotFoundMemberException("Associado não encontrado para o CPF: " + memberId));

        // Act & Assert
        assertThrows(NotFoundMemberException.class, () -> memberService.getMemberByCpf(cpf));

        verify(memberOutputPort, times(1)).findByCpf(cpf);
    }

    @Test
    void getAllMembers_ShouldReturnListOfMembers() throws NotFoundMemberException {
        // Arrange
        List<Member> memberList = List.of(member);
        when(memberOutputPort.findAll()).thenReturn(memberList);

        // Act
        List<Member> result = memberService.getAllMembers();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(memberOutputPort, times(1)).findAll();
    }

    @Test
    void getAllMembers_ShouldThrowNotFoundMemberException_WhenNotFound() throws NotFoundMemberException {
        // Arrange
        when(memberOutputPort.findAll()).thenThrow(new NotFoundMemberException("Nenhum associado encontrado"));

        // Act & Assert
        assertThrows(NotFoundMemberException.class, () -> memberService.getAllMembers());

        verify(memberOutputPort, times(1)).findAll();
    }

}
