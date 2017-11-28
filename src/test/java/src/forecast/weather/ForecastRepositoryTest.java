package src.forecast.weather;

import org.junit.Test;
import src.forecast.weather.domain.Forecast;

import java.io.IOException;

import static org.junit.Assert.*;

public class ForecastRepositoryTest {

    private final static String CITY_NAME = "Tallinn";
    private final static String COUNTRY_CODE = "EE";

    @Test
    public void shouldSendRequestAndGetCorrectResponse() throws IOException {
        ForecastRepository forecastRepository = new ForecastRepository();
        WeatherRequest request = WeatherRequest.withCity(CITY_NAME, COUNTRY_CODE);
        Forecast forecast = forecastRepository.getForecast(request);

        assertNotNull(forecast);
        assertEquals(CITY_NAME, forecast.city());
        assertTrue(forecast.days().size() == 3);
    }

}