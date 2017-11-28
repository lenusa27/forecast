package src.forecast.weather;

import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiRequest;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.Forecast;
import src.forecast.weather.domain.Temperature;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ForecastRepository {

    private static final String FORECAST_RESOURCE = "forecast";

    private OpenWeatherMapApi openWeatherMapApi = new OpenWeatherMapApi();

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
        parameters.put("q", weatherRequest.cityQuery());
        parameters.put("units", weatherRequest.units());
        return parameters;
    }

    private void logInput(WeatherRequest weatherRequest) {
        String cityOrCoordinates = weatherRequest.cityName() != null ? weatherRequest.cityName() : weatherRequest.latitude() + ":" + weatherRequest.longitude();
        FileLogger.input("Getting forecast for city: " + cityOrCoordinates);
    }

    private void logOutput(Forecast forecast) {
        StringBuilder output = new StringBuilder();
        
        for (Map.Entry<LocalDate, Temperature> entry : forecast.days().entrySet()) {
            output.append(entry.getKey()).append(": ").append(entry.getValue()).append(" | ");
        }
        
        FileLogger.output("Forecast in " + forecast.city() + " - " + output.toString());
    }

}
