package src.forecast.weather.domain;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;

public class Temperature {

    private BigDecimal current;
    private BigDecimal maximum;
    private BigDecimal minimum;

    public BigDecimal current() {
        return current;
    }

    public BigDecimal maximum() {
        return maximum;
    }

    public BigDecimal minimum() {
        return minimum;
    }

    Temperature(BigDecimal current, BigDecimal maximum, BigDecimal minimum) {
        this.current = current;
        this.maximum = maximum;
        this.minimum = minimum;
    }

    static Temperature fromJson(JsonNode node) {
        BigDecimal currentTemperature = new BigDecimal(node.get("temp").asText());
        BigDecimal maxTemperature = new BigDecimal(node.get("temp_max").asText());
        BigDecimal minTemperature = new BigDecimal(node.get("temp_min").asText());
        return new Temperature(currentTemperature, maxTemperature, minTemperature);
    }
}
