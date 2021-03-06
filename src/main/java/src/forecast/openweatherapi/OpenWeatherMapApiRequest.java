package src.forecast.openweatherapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class OpenWeatherMapApiRequest {

    private String resource;
    private Map<String, String> parameters;

    public OpenWeatherMapApiRequest(String resource, Map<String, String> parameters) {
        this.resource = resource;
        this.parameters = parameters;
    }

    String urlParameters(String apiKey) {
        StringBuilder urlParameters = new StringBuilder();

        if (resource != null) {
            urlParameters.append(resource);
        }

        if (apiKey != null) {
            urlParameters.append("?appid=").append(apiKey);
        }

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                String value = parameter.getValue();
                if (value != null) {
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        
                    }
                    urlParameters.append("&").append(parameter.getKey()).append("=").append(value);
                }
            }
        }

        return urlParameters.toString();
    }

}
