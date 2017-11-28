package src.forecast.weather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.Forecast;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ForecastRepositoryTest {

    private final static String CITY_NAME = "Tallinn";
    private final static String COUNTRY_CODE = "EE";

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
    public void shouldSendRequestAndGetCorrectResponse() throws IOException {
        ForecastRepository forecastRepository = new ForecastRepository(openWeatherMapApi, fileLogger);
        WeatherRequest request = WeatherRequest.withCity(CITY_NAME, COUNTRY_CODE);
        Forecast forecast = forecastRepository.getForecast(request);

        verify(openWeatherMapApi, times(1)).send(any());
        verify(fileLogger, times(1)).input(anyString());
        verify(fileLogger, times(1)).output(anyString(), anyString());
        
        assertNotNull(forecast);
        assertEquals(CITY_NAME, forecast.city());
        assertNotNull(forecast.latitude());
        assertNotNull(forecast.longitude());
        assertTrue(forecast.days().size() == 3);
    }

    private OpenWeatherMapApiResponse response() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("forecast_response.json").toURI());
        String jsonBody = new String(Files.readAllBytes(path));
        return OpenWeatherMapApiResponse.withJsonBody(jsonBody);
    }
    
}