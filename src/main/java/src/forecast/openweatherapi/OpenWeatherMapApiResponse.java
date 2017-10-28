package src.forecast.openweatherapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OpenWeatherMapApiResponse {

    private String jsonBody;

    public JsonNode jsonNode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonBody);
        return jsonNode;
    }

    static OpenWeatherMapApiResponse withJsonBody(String jsonBody) {
        OpenWeatherMapApiResponse response = new OpenWeatherMapApiResponse();
        response.jsonBody = jsonBody;
        return response;
    }

}
