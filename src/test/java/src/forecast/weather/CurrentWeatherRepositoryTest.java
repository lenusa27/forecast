package src.forecast.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.CurrentWeather;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CurrentWeatherRepositoryTest {

    private final static String CITY_NAME = "Tallinn";
    private final static String COUNTRY_CODE = "EE";
    private final static String CITY_LATITUDE = "59.436958";
    private final static String CITY_LONGITUDE = "24.753531";

    @Mock
    private OpenWeatherMapApi openWeatherMapApi;
    
    @Mock
    private FileLogger fileLogger;
    
    @Before
    public void setup() throws IOException, URISyntaxException {
        MockitoAnnotations.initMocks(this);
        
        when(openWeatherMapApi.send(any())).thenReturn(response());
    }
    
    @Test
    public void shouldSendRequestByCityNameAndGetCorrectResponse() throws IOException {
        CurrentWeatherRepository currentWeatherRepository = new CurrentWeatherRepository(openWeatherMapApi, fileLogger);
        WeatherRequest request = WeatherRequest.withCity(CITY_NAME, COUNTRY_CODE);
        CurrentWeather weather = currentWeatherRepository.getCurrentWeather(request);

        verify(openWeatherMapApi, times(1)).send(any());
        verify(fileLogger, times(1)).input(anyString());
        verify(fileLogger, times(1)).output(anyString(), anyString());
        
        assertNotNull(weather);
        assertEquals(CITY_NAME, weather.city());
        assertNotNull(weather.latitude());
        assertNotNull(weather.longitude());
        assertNotNull(weather.temperature().current());
        assertNotNull(weather.temperature().maximum());
        assertNotNull(weather.temperature().minimum());
    }

    @Test
    public void shouldSendRequestByCoordinatesNameAndGetCorrectResponse() throws IOException {
        CurrentWeatherRepository currentWeatherRepository = new CurrentWeatherRepository(openWeatherMapApi, fileLogger);
        WeatherRequest request = WeatherRequest.withCoordinates(CITY_LATITUDE, CITY_LONGITUDE);
        CurrentWeather weather = currentWeatherRepository.getCurrentWeather(request);

        verify(openWeatherMapApi, times(1)).send(any());
        verify(fileLogger, times(1)).input(anyString());
        verify(fileLogger, times(1)).output(anyString(), anyString());
        
        assertNotNull(weather);
        assertEquals(CITY_NAME, weather.city());
        assertNotNull(weather.latitude());
        assertNotNull(weather.longitude());
        assertNotNull(weather.temperature().current());
        assertNotNull(weather.temperature().maximum());
        assertNotNull(weather.temperature().minimum());
    }

    private OpenWeatherMapApiResponse response() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource("current_weather_response.json").toURI());
        String jsonBody = new String(Files.readAllBytes(path));
        return OpenWeatherMapApiResponse.withJsonBody(jsonBody);
    }
    
}