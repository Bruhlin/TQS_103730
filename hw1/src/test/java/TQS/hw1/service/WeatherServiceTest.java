package TQS.hw1.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;  
    
    @Test
    void testWeatherReturnsMap() {
        Map<?, ?> forecast = weatherService.getWeatherForecastByDate();

        assertNotNull(forecast);
    }
}
