package br.com.davilnv.cooperative.infrastructure.adapters.output.client;

import br.com.davilnv.cooperative.application.ports.output.VoteEligibilityOutputPort;
import br.com.davilnv.cooperative.domain.enums.VoteEligibility;
import br.com.davilnv.cooperative.domain.exception.VoteEligibilityNotFoundException;
import br.com.davilnv.cooperative.domain.exception.VoteEligibilitySystemException;
import br.com.davilnv.cooperative.infrastructure.adapters.output.client.dto.UserStatusResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class EligibilityRestAdapter implements VoteEligibilityOutputPort {

    private final RestTemplate restTemplate;

    public EligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VoteEligibility checkVotingEligibility(String cpf) throws VoteEligibilityNotFoundException, VoteEligibilitySystemException {

        String url = UriComponentsBuilder.fromHttpUrl("https://67169eb13fcb11b265d325c8.mockapi.io/api/v1/users")
                .queryParam("cpf", cpf)
                .toUriString();

        try {
            List<UserStatusResponse> responseList = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserStatusResponse>>() {
                    }).getBody();

            UserStatusResponse response = responseList != null && !responseList.isEmpty() ? responseList.get(0) : null;
            String status = response != null ? response.status() : null;

            if (status == null) {
                throw new Exception("[Eligibility System] Erro ao verificar a elegibilidade do usuário para o CPF: " + cpf);
            }

            return VoteEligibility.valueOf(status);
        } catch (HttpClientErrorException.NotFound e) {
            throw new VoteEligibilityNotFoundException("[Eligibility System] Usuário não encontrado para o CPF: " + cpf);
        } catch (Exception e) {
            e.printStackTrace();
            throw new VoteEligibilitySystemException("[Eligibility System] Erro externo ao verificar a elegibilidade do usuário para o CPF: " + cpf + " - " + e.getMessage());
        }
    }
}
