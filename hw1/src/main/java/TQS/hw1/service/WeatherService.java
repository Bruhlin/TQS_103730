package TQS.hw1.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {

    private final String API_KEY = "5f8c237a0adebc612b2de5b9a307cc04";

    private final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?lat=40.64&lon=-8.64&appid=" + API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("weather")
    public Map<LocalDate, String> getWeatherForecastByDate() {
        Map<LocalDate, String> forecasts = new HashMap<>();

        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        var list = (Iterable<Map<String, Object>>) response.get("list");

        for (Map<String, Object> entry : list) {
            String dateTime = (String) entry.get("dt_txt");
            LocalDate date = LocalDate.parse(dateTime.substring(0, 10));
            Map<String, Object> weather = ((List<Map<String, Object>>) entry.get("weather")).get(0);
            String description = (String) weather.get("description");

            forecasts.putIfAbsent(date, description);
        }

        return forecasts;
    }
}
