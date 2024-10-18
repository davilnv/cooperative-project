package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.AgendaEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.AgendaMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.AgendaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AgendaPersistenceAdapter implements AgendaOutputPort {

    private final AgendaRepository agendaRepository;
    private final AgendaMapper agendaMapper;

    public AgendaPersistenceAdapter(AgendaRepository agendaRepository, AgendaMapper agendaMapper) {
        this.agendaRepository = agendaRepository;
        this.agendaMapper = agendaMapper;
    }

    @Override
    public Agenda save(Agenda agenda) {
        AgendaEntity agendaEntity = agendaMapper.toEntity(agenda);
        return agendaMapper.toDomain(agendaRepository.save(agendaEntity));
    }

    @Override
    public Optional<Agenda> findById(UUID agendaId) {
        return agendaRepository.findById(agendaId)
                .map(agendaMapper::toDomain);
    }

    @Override
    public List<Agenda> findAll() {
        return agendaRepository.findAll()
                .stream()
                .map(agendaMapper::toDomain)
                .toList();
    }
}
