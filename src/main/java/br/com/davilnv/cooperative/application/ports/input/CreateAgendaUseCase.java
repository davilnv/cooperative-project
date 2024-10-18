package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.model.Agenda;

public interface CreateAgendaUseCase {

    Agenda createAgenda(Agenda agenda);

}
