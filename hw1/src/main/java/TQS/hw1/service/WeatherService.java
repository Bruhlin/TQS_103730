package TQS.hw1.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import TQS.hw1.cache.CacheStats;


@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final CacheManager cacheManager;
    private final CacheStats cacheStats;

    public WeatherService(RestTemplate restTemplate, CacheManager cacheManager, CacheStats cacheStats) {
        this.restTemplate = restTemplate;
        this.cacheManager = cacheManager;
        this.cacheStats = cacheStats;
    }

    public Map<LocalDate, String> getWeatherForecastByDate() {
        cacheStats.incrementRequests();
        logger.info("Fetching weather forecast from API");

        Cache cache = cacheManager.getCache("weather");
        Map<LocalDate, String> cached = cache != null ? cache.get("forecast", Map.class) : null;

        if (cached != null) {
            cacheStats.incrementHits();
            logger.info("Cache hit: returning cached forecast");
            return cached;
        }

        cacheStats.incrementMisses();
        logger.info("Cache miss: fetching from API");

        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=40.64&lon=-8.64&appid=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        if (response == null || !response.containsKey("list")) {
            logger.warn("Weather API response was null or missing 'list'");
            return Map.of();
        }

        Map<LocalDate, String> forecasts = new HashMap<>();
        var list = (Iterable<Map<String, Object>>) response.get("list");

        for (Map<String, Object> entry : list) {
            String dateTime = (String) entry.get("dt_txt");
            if (dateTime.endsWith("12:00:00")) {
                LocalDate date = LocalDate.parse(dateTime.substring(0, 10));
                Map<String, Object> weather = ((List<Map<String, Object>>) entry.get("weather")).get(0);
                String main = (String) weather.get("main");
                forecasts.putIfAbsent(date, main);
            }
        }

        logger.info("Fetched {} forecasts", forecasts.size());

        if (cache != null) {
            cache.put("forecast", forecasts);
            logger.info("Forecasts stored in cache");
        }

        return forecasts;
    }
}
