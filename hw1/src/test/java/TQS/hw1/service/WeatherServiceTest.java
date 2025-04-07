package TQS.hw1.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {
    
    @Test
    void testWeatherReturnsMap() {
        WeatherService weatherService = new WeatherService();
        Map<?, ?> forecast = weatherService.getWeatherForecastByDate();

        assertNotNull(forecast);
    }
}
