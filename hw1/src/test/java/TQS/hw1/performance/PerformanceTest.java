package TQS.hw1.performance;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testMealApiPerformance() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            String response = restTemplate.getForObject("http://localhost:8080/api/meals?restaurant=CantinaCastro", String.class);
            assertTrue(response != null && response.contains("restaurant"));
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Completed 100 requests in " + duration + "ms");
        assertTrue(duration < 5000); // espera que esteja dentro de 5 segundos
    }
}
