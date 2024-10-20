package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class BaseRestTest {
    protected static final String API_BASE_V1 = "/api/v1/";
    private static final String JSON_PATH = "src/test/resources/static/files/";
    protected static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;
    protected String baseEndpoint;
    protected String json;

    protected String getJson(String fileName) throws IOException {
        File jsonFile = new File(JSON_PATH + fileName + ".json");
        return new String(Files.readAllBytes(jsonFile.toPath()));
    }
}
