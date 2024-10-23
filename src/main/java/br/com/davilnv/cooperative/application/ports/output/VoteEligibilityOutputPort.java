package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.enums.VoteEligibility;
import br.com.davilnv.cooperative.domain.exception.VoteEligibilityNotFoundException;
import br.com.davilnv.cooperative.domain.exception.VoteEligibilitySystemException;

public interface VoteEligibilityOutputPort {

    VoteEligibility checkVotingEligibility(String cpf) throws VoteEligibilityNotFoundException, VoteEligibilitySystemException;

}
