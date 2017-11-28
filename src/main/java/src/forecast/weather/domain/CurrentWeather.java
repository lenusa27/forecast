package src.forecast.weather.domain;

import com.fasterxml.jackson.databind.JsonNode;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;

import java.io.IOException;

public class CurrentWeather extends Weather {

    private Temperature temperature;

    public Temperature temperature() {
        return temperature;
    }

    public static CurrentWeather fromOpenWeatherMapApi(OpenWeatherMapApiResponse response) throws IOException {
        JsonNode jsonNode = response.jsonNode();
        CurrentWeather currentWeather = new CurrentWeather();
        if (jsonNode.has("name")) {
            currentWeather.city = jsonNode.get("name").asText();
            currentWeather.temperature = Temperature.fromJson(jsonNode.get("main"));
        } else {
            throw new IllegalArgumentException("City is not found");
        }
        return currentWeather;
    }

}
