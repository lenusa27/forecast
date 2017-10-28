package src.forecast.openweatherapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class OpenWeatherMapApi {

    private final static String API_URL = "http://api.openweathermap.org/data/2.5/";
    private final static String API_KEY = "1f9feb251b7d9ebaf270b32f78c585ef";

    public OpenWeatherMapApiResponse send(OpenWeatherMapApiRequest request) throws IOException {
        String urlString = API_URL + request.urlParameters(API_KEY);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(urlString);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        String jsonBody = response.readEntity(String.class);
        return OpenWeatherMapApiResponse.withJsonBody(jsonBody);
    }

}