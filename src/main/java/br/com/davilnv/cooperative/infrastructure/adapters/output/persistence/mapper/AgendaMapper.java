package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.AgendaEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.AgendaStatusEntity;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {
    public AgendaEntity toEntity(Agenda agenda) {
        return new AgendaEntity(
                agenda.getId(),
                agenda.getTitle(),
                agenda.getDescription(),
                AgendaStatusEntity.valueOf(agenda.getStatus().name()),
                agenda.getStartDateTime(),
                agenda.getEndDateTime()
        );
    }

    public Agenda toDomain(AgendaEntity agendaEntity) {
        return new Agenda(
                agendaEntity.getId(),
                agendaEntity.getTitle(),
                agendaEntity.getDescription(),
                AgendaStatus.valueOf(agendaEntity.getStatus().name()),
                agendaEntity.getStartDateTime(),
                agendaEntity.getEndDateTime()
        );
    }
}
