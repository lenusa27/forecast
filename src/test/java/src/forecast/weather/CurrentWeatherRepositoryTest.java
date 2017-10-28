package src.forecast.weather;

import org.junit.Test;
import src.forecast.weather.domain.CurrentWeather;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrentWeatherRepositoryTest {

    private final static String CITY_NAME = "Tallinn";
    private final static String COUNTRY_CODE = "EE";
    private final static String CITY_LATITUDE = "59.436958";
    private final static String CITY_LONGITUDE = "24.753531";
    private final static String UNITS = "metric";

    @Test
    public void shouldSendRequestByCityNameAndGetCorrectResponse() throws IOException {
        CurrentWeatherRepository currentWeatherRepository = new CurrentWeatherRepository();
        WeatherRequest request = WeatherRequest.withCity(CITY_NAME, COUNTRY_CODE, UNITS);
        CurrentWeather weather = currentWeatherRepository.getCurrentWeather(request);

        assertNotNull(weather);
        assertEquals(CITY_NAME, weather.city());
        assertNotNull(weather.temperature().current());
        assertNotNull(weather.temperature().maximum());
        assertNotNull(weather.temperature().minimum());
    }

    @Test
    public void shouldSendRequestByCoordinatesNameAndGetCorrectResponse() throws IOException {
        CurrentWeatherRepository currentWeatherRepository = new CurrentWeatherRepository();
        WeatherRequest request = WeatherRequest.withCoordinates(CITY_LATITUDE, CITY_LONGITUDE, UNITS);
        CurrentWeather weather = currentWeatherRepository.getCurrentWeather(request);

        assertNotNull(weather);
        assertEquals(CITY_NAME, weather.city());
        assertNotNull(weather.temperature().current());
        assertNotNull(weather.temperature().maximum());
        assertNotNull(weather.temperature().minimum());
    }

}