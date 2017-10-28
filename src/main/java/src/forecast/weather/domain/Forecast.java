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

    private static DateTimeFormatter YYYY_MM_DD = ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<LocalDate, Temperature> days = new HashMap<>();

    public Map<LocalDate, Temperature> days() {
        return days;
    }

    public static Forecast fromOpenWeatherMapApi(OpenWeatherMapApiResponse response) throws IOException {
        JsonNode jsonNode = response.jsonNode();
        Forecast forecast = new Forecast();
        forecast.city = jsonNode.get("city").get("name").asText();
        JsonNode list = jsonNode.get("list");
        if (list.isArray()) {
            String firstDate = list.get(0).get("dt_txt").asText();
            LocalDate localDate = LocalDate.parse(firstDate, YYYY_MM_DD);

            for (int i = 0; i < 3; i ++) {
                LocalDate day = localDate.plusDays(i);
                forecast.days.put(day, dayTemperature(day, list));
            }
        }
        return forecast;
    }

    private static Temperature dayTemperature(LocalDate day, JsonNode list) {
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

        return new Temperature(null, dayMaxTemperature, dayMinTemperature);
    }
}
