package TQS.hw1.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.cache.CacheStats;

@RestController
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
    
    private final CacheStats cacheStats;

    public CacheController(CacheStats cacheStats) {
        this.cacheStats = cacheStats;
    }

    @GetMapping("/api/cache/status")
    public Map<String, Integer> getCacheStatus() {
        logger.info("Cache status called");
        return Map.of(
            "totalRequests", cacheStats.getTotalRequests(),
            "hits", cacheStats.getHits(),
            "misses", cacheStats.getMisses()
        );
    }
}
