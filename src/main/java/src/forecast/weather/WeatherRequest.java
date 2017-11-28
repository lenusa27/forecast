package src.forecast.weather;

public class WeatherRequest {

    private static final String DEFAULT_UNITS = "metric";
    
    private String cityName;
    private String countryCode;
    private String units = DEFAULT_UNITS;

    private String latitude;
    private String longitude;

    public static WeatherRequest withCity(String cityName) {
        return withCity(cityName, null);
    }
    
    public static WeatherRequest withCity(String cityName, String countryCode) {
        WeatherRequest request = new WeatherRequest();
        request.cityName = cityName;
        request.countryCode = countryCode;
        return request;
    }

    public static WeatherRequest withCoordinates(String latitude, String longitude) {
        WeatherRequest request = new WeatherRequest();
        request.latitude = latitude;
        request.longitude = longitude;
        return request;
    }
    
    public String cityQuery() {
        return cityName + (countryCode != null ? "," + countryCode : "");
    }

    public String cityName() {
        return cityName;
    }

    public String countryCode() {
        return countryCode;
    }

    public String units() {
        return units;
    }

    public String latitude() {
        return latitude;
    }

    public String longitude() {
        return longitude;
    }
}
