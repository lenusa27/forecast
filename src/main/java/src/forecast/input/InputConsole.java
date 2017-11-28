package src.forecast.input;

import src.forecast.logger.FileLogger;
import src.forecast.openweatherapi.OpenWeatherMapApi;
import src.forecast.weather.CurrentWeatherRepository;
import src.forecast.weather.ForecastRepository;
import src.forecast.weather.WeatherRequest;

import java.io.IOException;
import java.util.Scanner;

public class InputConsole {
	
	private static OpenWeatherMapApi openWeatherMapApi = new OpenWeatherMapApi();
	private static FileLogger fileLogger = new FileLogger();
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter city: ");
		String city = scanner.nextLine();
		
		while (true) {
			System.out.println("Press:\n1 - for current weather\n2 - for forecast\n0 - to exit");
			String input = scanner.nextLine();
			
			if (input.equals("0")) {
				break;
			}
			
			if (input.equals("1")) {
				currentWeather(city);
				break;
			}

			if (input.equals("2")) {
				forecast(city);
				break;
			}
		}
		
		scanner.close();
	}
	
	private static void currentWeather(String cityName) throws IOException {
		CurrentWeatherRepository currentWeatherRepository = new CurrentWeatherRepository(openWeatherMapApi, fileLogger);
		WeatherRequest request = WeatherRequest.withCity(cityName);
		currentWeatherRepository.getCurrentWeather(request);
	}
	
	private static void forecast(String cityName) throws IOException {
		ForecastRepository forecastRepository = new ForecastRepository(openWeatherMapApi, fileLogger);
		WeatherRequest request = WeatherRequest.withCity(cityName);
		forecastRepository.getForecast(request);
	}
	
}
