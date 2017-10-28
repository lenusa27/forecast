package src.forecast.openweatherapi;

import java.util.Map;

public class OpenWeatherMapApiRequest {

    private String resource;
    private Map<String, String> parameters;

    public OpenWeatherMapApiRequest(String resource, Map<String, String> parameters) {
        this.resource = resource;
        this.parameters = parameters;
    }

    String urlParameters(String apiKey) {
        String urlParameters = "";

        if (resource != null) {
            urlParameters += resource;
        }

        if (apiKey != null) {
            urlParameters += "?appid=" + apiKey;
        }

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry parameter : parameters.entrySet()) {
                urlParameters += "&" + parameter.getKey() + "=" + parameter.getValue();
            }
        }

        return urlParameters;
    }

}
