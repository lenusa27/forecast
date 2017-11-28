package src.forecast.weather.domain;

import com.fasterxml.jackson.databind.JsonNode;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Forecast extends Weather {

    private static final int DAYS_WITH_FORECAST = 3; 
    private static DateTimeFormatter YYYY_MM_DD = ofPattern("yyyy-MM-dd HH:mm:ss");

    private List<DayForecast> days = new ArrayList<>();

    public List<DayForecast> days() {
        return days;
    }

    public static Forecast fromOpenWeatherMapApi(OpenWeatherMapApiResponse response) throws IOException {
        JsonNode jsonNode = response.jsonNode();
        Forecast forecast = new Forecast();

        if (jsonNode.has("city")) {
            JsonNode cityNode = jsonNode.get("city");
            forecast.city = cityNode.get("name").asText();
            
            JsonNode coordinates = cityNode.get("coord");
            forecast.latitude = coordinates.get("lat").asText();
            forecast.longitude = coordinates.get("lon").asText();
            
            forecast.days = daysForecast(jsonNode);
        } else {
            throw new IllegalArgumentException("City is not found");
        }
        return forecast;
    }
    
    private static List<DayForecast> daysForecast(JsonNode node) {
        List<DayForecast> dayForecasts = new ArrayList<>();
        JsonNode list = node.get("list");
        if (list.isArray()) {
            String firstDate = list.get(0).get("dt_txt").asText();
            LocalDate localDate = LocalDate.parse(firstDate, YYYY_MM_DD);

            for (int i = 0; i < DAYS_WITH_FORECAST; i++) {
                LocalDate day = localDate.plusDays(i);
                dayForecasts.add(dayTemperature(day, list));
            }
        }
        return dayForecasts;
    }

    private static DayForecast dayTemperature(LocalDate day, JsonNode list) {
        List<Temperature> dayTemperatures = new ArrayList<>();
        for (JsonNode node : list) {
            String dateString = node.get("dt_txt").asText();
            LocalDate date = LocalDate.parse(dateString, YYYY_MM_DD);

            if (day.isEqual(date)) {
                dayTemperatures.add(Temperature.fromJson(node.get("main")));
            }
        }

        BigDecimal dayMaxTemperature = dayTemperatures.stream()
                .map(Temperature::maximum)
                .max(Comparator.naturalOrder())
                .orElse(null);

        BigDecimal dayMinTemperature = dayTemperatures.stream()
                .map(Temperature::minimum)
                .min(Comparator.naturalOrder())
                .orElse(null);

        return new DayForecast(day, new Temperature(null, dayMaxTemperature, dayMinTemperature));
    }
}
