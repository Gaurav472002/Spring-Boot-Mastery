package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;


//    private static final String API =
//            "http://api.weatherstack.com/current?access_key=%s&query=%s";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public String getWeather(String city) {

        String finalAPI = String.format(appCache.APP_CACHE.get(AppCache.Keys.WEATHER_API.toString()), apiKey, city);

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalAPI,
                        HttpMethod.GET,
                        null,
                        WeatherResponse.class);

        WeatherResponse body = response.getBody();

        if(body == null || body.getCurrent() == null){
            return "Weather unavailable";
        }

        return body.getCurrent().getTemperature() + "°C, "
                + body.getCurrent().getWeatherDescription().get(0)
                + ", Feels like "
                + body.getCurrent().getFeelslike() + "°C";
    }



    // POST request using RestTemplate
    public String sendPost(PostRequest postRequest) {

        String url =
                "https://jsonplaceholder.typicode.com/posts";


        // 1. Create headers
        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );


        // 2. Combine body + headers
        HttpEntity<PostRequest> requestEntity =
                new HttpEntity<>(
                        postRequest,
                        headers
                );


        // 3. Send POST request
        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );


        // 4. Get response body
        return response.getBody();
    }


}
