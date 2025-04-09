package TQS.hw1.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;  
    
    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
	void setUp() {
		Map<String, Object> mockedResponse = new HashMap<>();
		mockedResponse.put("list", List.of(
			Map.of("dt_txt", "2025-04-10 12:00:00", "weather", List.of(Map.of("main", "clear")))
		));
		
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Map.class))).thenReturn(mockedResponse);
	}

    @Test
    void testWeatherReturnsMap() {
        Map<?, ?> forecast = weatherService.getWeatherForecastByDate();

        assertNotNull(forecast);
        assertFalse(forecast.isEmpty());
    }
}
