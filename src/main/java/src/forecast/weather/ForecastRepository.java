package src.forecast.weather;

import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiRequest;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.DayForecast;
import src.forecast.weather.domain.Forecast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForecastRepository {

    private static final String FORECAST_RESOURCE = "forecast";

    private OpenWeatherMapApi openWeatherMapApi;
    private FileLogger fileLogger;
    
    public ForecastRepository(OpenWeatherMapApi openWeatherMapApi, FileLogger fileLogger) {
        this.openWeatherMapApi = openWeatherMapApi;
        this.fileLogger = fileLogger;
    }

    public Forecast getForecast(WeatherRequest weatherRequest) throws IOException {
        logInput(weatherRequest);
        
        Map<String, String> parameters = forecastRequestParameters(weatherRequest);
        OpenWeatherMapApiRequest request = new OpenWeatherMapApiRequest(FORECAST_RESOURCE, parameters);
        OpenWeatherMapApiResponse response = openWeatherMapApi.send(request);
        Forecast forecast = Forecast.fromOpenWeatherMapApi(response);
        
        logOutput(forecast);
        
        return forecast;
    }

    private Map<String, String> forecastRequestParameters(WeatherRequest weatherRequest) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", weatherRequest.citySearchQuery());
        parameters.put("units", weatherRequest.units());
        return parameters;
    }

    private void logInput(WeatherRequest weatherRequest) throws IOException {
        String cityOrCoordinates = weatherRequest.cityName() != null ? weatherRequest.cityName() : weatherRequest.latitude() + ":" + weatherRequest.longitude();
        String content = "Getting forecast for city: " + cityOrCoordinates;
		fileLogger.input(content);
    }

    private void logOutput(Forecast forecast) throws IOException {
        StringBuilder output = new StringBuilder();
        
        for (DayForecast dayForecast : forecast.days()) {
            output.append(dayForecast.date()).append(": ").append(dayForecast.temperature()).append(" | ");
        }

        String fileName = forecast.city() + ".txt";
        String content = "Forecast in " + forecast.cityAsText() + " - " + output.toString();
		fileLogger.output(fileName, content);
    }

}
