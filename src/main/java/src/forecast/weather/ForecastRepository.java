package src.forecast.weather;

import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiRequest;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.Forecast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForecastRepository {

    private static final String FORECAST_RESOURCE = "forecast";

    private OpenWeatherMapApi openWeatherMapApi = new OpenWeatherMapApi();

    public Forecast getForecast(WeatherRequest weatherRequest) throws IOException {
        Map<String, String> parameters = forecastRequestParameters(weatherRequest);
        OpenWeatherMapApiRequest request = new OpenWeatherMapApiRequest(FORECAST_RESOURCE, parameters);
        OpenWeatherMapApiResponse response = openWeatherMapApi.send(request);
        return Forecast.fromOpenWeatherMapApi(response);
    }

    private Map<String, String> forecastRequestParameters(WeatherRequest weatherRequest) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", weatherRequest.cityName() + "," + weatherRequest.countryCode());
        parameters.put("units", weatherRequest.units());
        return parameters;
    }

}
