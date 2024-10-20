package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "CPF", nullable = false, length = 11)
    private String cpf;

    @Column(name = "MEMBER_NAME", nullable = false, length = 150)
    private String memberName;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    public MemberEntity() {
    }

    public MemberEntity(UUID id, String cpf, String memberName, LocalDate birthDate) {
        this.id = id;
        this.cpf = cpf;
        this.memberName = memberName;
        this.birthDate = birthDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
