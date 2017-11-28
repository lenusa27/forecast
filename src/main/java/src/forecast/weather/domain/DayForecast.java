package src.forecast.weather.domain;

import java.time.LocalDate;

public class DayForecast {
	
	private LocalDate date;
	private Temperature temperature;
	
	DayForecast(LocalDate date, Temperature temperature) {
		this.date = date;
		this.temperature = temperature;
	}

	public LocalDate date() {
		return date;
	}

	public Temperature temperature() {
		return temperature;
	}
}
