package src.forecast.weather;

import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.openweatherapi.OpenWeatherMapApiRequest;
import src.forecast.openweatherapi.OpenWeatherMapApiResponse;
import src.forecast.weather.domain.CurrentWeather;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CurrentWeatherRepository {

    private static final String CURRENT_WEATHER_RESOURCE = "weather";

    private OpenWeatherMapApi openWeatherMapApi;
	private FileLogger fileLogger;
    
	public CurrentWeatherRepository(OpenWeatherMapApi openWeatherMapApi, FileLogger fileLogger) {
		this.openWeatherMapApi = openWeatherMapApi;
		this.fileLogger = fileLogger;
	}
	
    public CurrentWeather getCurrentWeather(WeatherRequest weatherRequest) throws IOException {
    	logInput(weatherRequest);
    	
        Map<String, String> parameters = currentWeatherRequestParameters(weatherRequest);
        OpenWeatherMapApiRequest request = new OpenWeatherMapApiRequest(CURRENT_WEATHER_RESOURCE, parameters);
        OpenWeatherMapApiResponse response = openWeatherMapApi.send(request);
        CurrentWeather currentWeather = CurrentWeather.fromOpenWeatherMapApi(response);
        
        logOutput(currentWeather);
        
        return currentWeather;
    }

    private Map<String, String> currentWeatherRequestParameters(WeatherRequest weatherRequest) {
        Map<String, String> parameters = new HashMap<>();

        if (weatherRequest.cityName() != null) {
            parameters.put("q", weatherRequest.citySearchQuery());
        } else if (weatherRequest.latitude() != null && weatherRequest.longitude() != null) {
            parameters.put("lat", weatherRequest.latitude());
            parameters.put("lon", weatherRequest.longitude());
        }
        parameters.put("units", weatherRequest.units());
        return parameters;
    }
    
    private void logInput(WeatherRequest weatherRequest) throws IOException {
    	String cityOrCoordinates = weatherRequest.cityName() != null ? weatherRequest.cityName() : weatherRequest.latitude() + ":" + weatherRequest.longitude();
    	String content = "Getting current weather for city: " + cityOrCoordinates;
    	fileLogger.input(content);
	}
	
	private void logOutput(CurrentWeather currentWeather) throws IOException {
    	String fileName = currentWeather.city() + ".txt";
    	String content = "Current weather in " + currentWeather.cityAsText() + " - " + currentWeather.temperature();
    	fileLogger.output(fileName, content);
	}

}
