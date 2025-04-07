package TQS.hw1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import TQS.hw1.cache.CacheStats;

@RestController
public class CacheController {
    
    private final CacheStats cacheStats;

    public CacheController(CacheStats cacheStats) {
        this.cacheStats = cacheStats;
    }

    @GetMapping("/api/cache/status")
    public Map<String, Integer> getCacheStats() {
        return Map.of(
            "totalRequests", cacheStats.getTotalRequests(),
            "hits", cacheStats.getHits(),
            "misses", cacheStats.getMisses()
        );
    }
}
