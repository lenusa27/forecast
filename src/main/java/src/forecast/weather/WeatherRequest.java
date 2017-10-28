package src.forecast.weather;

public class WeatherRequest {

    private String cityName;
    private String countryCode;
    private String units;

    private String latitude;
    private String longitude;

    public static WeatherRequest withCity(String cityName, String countryCode, String units) {
        WeatherRequest request = new WeatherRequest();
        request.cityName = cityName;
        request.countryCode = countryCode;
        request.units = units;
        return request;
    }

    public static WeatherRequest withCoordinates(String latitude, String longitude, String units) {
        WeatherRequest request = new WeatherRequest();
        request.latitude = latitude;
        request.longitude = longitude;
        request.units = units;
        return request;
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
