package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    public WeatherResponse getWeather(String city) {

        String cacheKey = "weather_of_" + city.toLowerCase().trim();

        // 1. Check Redis
        WeatherResponse weatherResponse =
                redisService.get(cacheKey, WeatherResponse.class);

        if (weatherResponse != null) {
            System.out.println("Weather fetched from Redis for: " + city);
            return weatherResponse;
        }

        // 2. Redis cache miss
        System.out.println("Weather not found in Redis. Calling API for: " + city);

        String finalAPI = String.format(
                appCache.APP_CACHE.get(
                        AppCache.Keys.WEATHER_API.toString()
                ),
                apiKey,
                city
        );

        // 3. Call Weather API
        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(
                        finalAPI,
                        HttpMethod.GET,
                        null,
                        WeatherResponse.class
                );

        WeatherResponse body = response.getBody();

        // 4. Handle invalid/unavailable weather response
        if (body == null || body.getCurrent() == null) {
            System.out.println("Weather unavailable for: " + city);
            return null;
        }

        // 5. Store complete WeatherResponse in Redis for 5 minutes
        redisService.set(
                cacheKey,
                body,
                300L
        );

        System.out.println("Weather stored in Redis for: " + city);

        // 6. Return complete response
        return body;
    }


    private String formatWeatherResponse(WeatherResponse weatherResponse) {

        if (weatherResponse == null ||
                weatherResponse.getCurrent() == null) {

            return "Weather unavailable";
        }

        String description = "Unknown";

        if (weatherResponse.getCurrent().getWeatherDescription() != null &&
                !weatherResponse.getCurrent().getWeatherDescription().isEmpty()) {

            description =
                    weatherResponse.getCurrent()
                            .getWeatherDescription()
                            .get(0);
        }

        return weatherResponse.getCurrent().getTemperature() + "°C, "
                + description
                + ", Feels like "
                + weatherResponse.getCurrent().getFeelslike()
                + "°C";
    }


    // POST request using RestTemplate
    public String sendPost(PostRequest postRequest) {

        String url =
                "https://jsonplaceholder.typicode.com/posts";

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<PostRequest> requestEntity =
                new HttpEntity<>(
                        postRequest,
                        headers
                );

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );

        return response.getBody();
    }
}