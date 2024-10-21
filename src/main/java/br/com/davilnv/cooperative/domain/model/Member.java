package br.com.davilnv.cooperative.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Member {
    private UUID id;
    private String cpf;
    private String memberName;
    private LocalDate birthDate;

    public Member() {
    }

    public Member(UUID id) {
        this.id = id;
    }

    public Member(UUID id, String cpf, String memberName, LocalDate birthDate) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Member member = (Member) obj;
        return id.equals(member.id) && cpf.equals(member.cpf);
    }
}
