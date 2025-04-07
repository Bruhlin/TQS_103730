package TQS.hw1.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheStatsTest {

	@Test
	void testIncrements() {
		CacheStats stats = new CacheStats();

		stats.incrementRequests();
		stats.incrementRequests();
		stats.incrementHits();
		stats.incrementMisses();
		stats.incrementMisses();

		assertEquals(2, stats.getTotalRequests());
		assertEquals(1, stats.getHits());
		assertEquals(2, stats.getMisses());
	}

}
