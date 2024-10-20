package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MemberPersistenceAdapterTest {

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Test
    @Order(1)
    void save_ShouldPersistMember_WhenValid() throws MemberAlreadyExistsException {
        // Arrange
        Member member = new Member(
                null,
                "81269707078",
                "João José",
                LocalDate.of(2000, 10, 10)
        );

        // Act
        Member savedMember = memberPersistenceAdapter.save(member);

        // Assert
        assertNotNull(savedMember);
        assertNotNull(savedMember.getId());
        assertEquals(member.getCpf(), savedMember.getCpf());
    }

    @Test
    @Order(2)
    void save_ShouldThrowMemberAlreadyExistsException_WhenNotValid() {
        // Arrange
        Member member = new Member(
                null,
                "81269707078",
                "João José",
                LocalDate.of(2000, 10, 10)
        );

        // Act & Assert
        assertThrows(MemberAlreadyExistsException.class, () -> memberPersistenceAdapter.save(member));
    }

    @Test
    @Order(3)
    void findById_ShouldReturnMember_WhenValid() throws NotFoundMemberException, MemberAlreadyExistsException {
        // Arrange
        Member member = new Member(
                null,
                "19833422039",
                "Maria José",
                LocalDate.of(2000, 11, 11)
        );

        Member savedMember = memberPersistenceAdapter.save(member);

        // Act
        Member foundMember = memberPersistenceAdapter.findById(savedMember.getId());

        // Assert
        assertNotNull(foundMember);
        assertEquals(savedMember, foundMember);
        assertEquals(savedMember.getCpf(), foundMember.getCpf());
    }

    @Test
    @Order(4)
    void findById_ShouldThrowNotFoundMemberException_WhenNotValid() throws MemberAlreadyExistsException {
        // Arrange
        Member member = new Member(
                null,
                "44136731078",
                "Pietra Leticia",
                LocalDate.of(2001, 1, 10)
        );

        memberPersistenceAdapter.save(member);
        UUID notValidId = UUID.randomUUID();

        // Act & Assert
        assertThrows(NotFoundMemberException.class, () -> memberPersistenceAdapter.findById(notValidId));
    }


    @Test
    @Order(5)
    void findByCpf_ShouldReturnMember_WhenValid() throws NotFoundMemberException, MemberAlreadyExistsException {
        // Arrange
        Member member = new Member(
                null,
                "94728179044",
                "Petrucio Amorim",
                LocalDate.of(2004, 6, 12)
        );

        Member savedMember = memberPersistenceAdapter.save(member);

        // Act
        Member foundMember = memberPersistenceAdapter.findByCpf(savedMember.getCpf());

        // Assert
        assertNotNull(foundMember);
        assertEquals(savedMember, foundMember);
        assertEquals(savedMember.getCpf(), foundMember.getCpf());
    }

    @Test
    @Order(6)
    void findByCpf_ShouldThrowNotFoundMemberException_WhenNotValid() throws MemberAlreadyExistsException {
        // Arrange
        String cpf = "58944531005";
        Member member = new Member(
                null,
                "36805723019",
                "João Junior",
                LocalDate.of(2001, 1, 10)
        );

        memberPersistenceAdapter.save(member);

        // Act & Assert
        assertThrows(NotFoundMemberException.class, () -> memberPersistenceAdapter.findByCpf(cpf));
    }

    @Test
    @Order(7)
    void findAll_ShouldReturnAllMembers_WhenValid() throws NotFoundMemberException, MemberAlreadyExistsException {
        // Arrange
        Member member1 = new Member(
                null,
                "17332274078",
                "Junior Lima",
                LocalDate.of(2003, 12, 13)
        );

        Member member2 = new Member(
                null,
                "19992487089",
                "Davi Lima",
                LocalDate.of(1998, 12, 28)
        );

        memberPersistenceAdapter.save(member1);
        memberPersistenceAdapter.save(member2);

        // Act
        List<Member> members = memberPersistenceAdapter.findAll();

        // Assert
        assertNotNull(members);
        assertFalse(members.isEmpty());
        assertEquals(4, members.size());
    }

}
