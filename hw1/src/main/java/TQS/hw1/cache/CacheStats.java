package TQS.hw1.cache;

import org.springframework.stereotype.Component;

@Component
public class CacheStats {
    private int totalRequests = 0;
    private int hits = 0;
    private int misses = 0;

    public void incrementRequests() {totalRequests++;}
    public void incrementHits() {hits++;}
    public void incrementMisses() {misses++;}

    public int getTotalRequests() {return totalRequests;}
    public int getHits() {return hits;}
    public int getMisses() {return misses;}
}


