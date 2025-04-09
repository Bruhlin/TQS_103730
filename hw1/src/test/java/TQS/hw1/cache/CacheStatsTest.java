package TQS.hw1.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import TQS.hw1.service.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
class CacheStatsTest {

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private CacheStats cacheStats;

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
	void cacheTests() {
		weatherService.getWeatherForecastByDate();
		assertEquals(1, cacheStats.getTotalRequests());
		assertEquals(1, cacheStats.getMisses());

		weatherService.getWeatherForecastByDate();
		assertEquals(2, cacheStats.getTotalRequests());
		assertEquals(1, cacheStats.getHits());
		assertEquals(1, cacheStats.getMisses());
	}

}
