package src.forecast.weather.domain;

abstract class Weather {

    String city;
    String latitude;
    String longitude;

    public String city() {
        return city;
    }

    public String latitude() {
        return latitude;
    }

    public String longitude() {
        return longitude;
    }
    
    public String cityAsText() {
        return city + "(" + latitude + ":" + longitude + ")";
    }
    
}
