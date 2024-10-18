package br.com.davilnv.cooperative.infrastructure.adapters.output.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExampleRestAdapter {

    private final RestTemplate restTemplate;

    public ExampleRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void exampleMethod() {
        // Do something
        //restTemplate.getForObject("http://localhost:8080/example", String.class);
    }
}
