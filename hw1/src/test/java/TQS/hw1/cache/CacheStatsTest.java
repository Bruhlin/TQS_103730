package TQS.hw1.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import TQS.hw1.service.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
class CacheStatsTest {

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private CacheStats cacheStats;

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
