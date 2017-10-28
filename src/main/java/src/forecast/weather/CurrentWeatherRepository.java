package src.forecast.weather;

import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiRequest;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.CurrentWeather;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CurrentWeatherRepository {

    private static final String CURRENT_WEATHER_RESOURCE = "weather";

    private OpenWeatherMapApi openWeatherMapApi = new OpenWeatherMapApi();

    public CurrentWeather getCurrentWeather(WeatherRequest weatherRequest) throws IOException {
        Map<String, String> parameters = currentWeatherRequestParameters(weatherRequest);
        OpenWeatherMapApiRequest request = new OpenWeatherMapApiRequest(CURRENT_WEATHER_RESOURCE, parameters);
        OpenWeatherMapApiResponse response = openWeatherMapApi.send(request);
        return CurrentWeather.fromOpenWeatherMapApi(response);
    }

    private Map<String, String> currentWeatherRequestParameters(WeatherRequest weatherRequest) {
        Map<String, String> parameters = new HashMap<>();

        if (weatherRequest.cityName() != null && weatherRequest.countryCode() != null) {
            parameters.put("q", weatherRequest.cityName() + "," + weatherRequest.countryCode());
        } else if (weatherRequest.latitude() != null && weatherRequest.longitude() != null) {
            parameters.put("lat", weatherRequest.latitude());
            parameters.put("lon", weatherRequest.longitude());
        }
        parameters.put("units", weatherRequest.units());
        return parameters;
    }

}
