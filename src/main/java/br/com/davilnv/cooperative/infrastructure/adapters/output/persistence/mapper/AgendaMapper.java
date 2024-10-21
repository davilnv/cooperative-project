package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.AgendaEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.enums.AgendaStatusEntity;

public class AgendaMapper {

    public static AgendaEntity toEntity(Agenda agenda) {
        AgendaEntity agendaEntity = new AgendaEntity(
                agenda.getId(),
                agenda.getTitle(),
                agenda.getDescription(),
                AgendaStatusEntity.valueOf(agenda.getStatus().name()),
                agenda.getStartDateTime(),
                agenda.getEndDateTime(),
                agenda.getVotingSession() != null ? VotingSessionMapper.toEntity(agenda.getVotingSession()) : null
        );

        agendaEntity.setVotesYes(agenda.getVotesYes());
        agendaEntity.setVotesNo(agenda.getVotesNo());
        return agendaEntity;
    }

    public static Agenda toDomain(AgendaEntity agendaEntity) {
        Agenda agenda = new Agenda(
                agendaEntity.getId(),
                agendaEntity.getTitle(),
                agendaEntity.getDescription(),
                AgendaStatus.valueOf(agendaEntity.getStatus().name()),
                agendaEntity.getStartDateTime(),
                agendaEntity.getEndDateTime()
        );

        agenda.setVotesYes(agendaEntity.getVotesYes());
        agenda.setVotesNo(agendaEntity.getVotesNo());
        return agenda;
    }
}
