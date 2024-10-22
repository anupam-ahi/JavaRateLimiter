package org.example.apistats.apis;

import java.util.concurrent.TimeUnit;


public class TokenBucket {
    private final long maxTokens;
    private final long refillRate; // tokens per second
    private long currentTokens;
    private long lastRefillTimestamp;

    public TokenBucket(long maxTokens, long refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.currentTokens = maxTokens; // start full
        this.lastRefillTimestamp = System.nanoTime();
    }

    // Method to attempt consuming a token
    public synchronized boolean allowRequest() {
        refill();

        if (currentTokens > 0) {
            currentTokens--;
            return true; // Request allowed
        }

        return false; // Request denied (no tokens left)
    }

    // Refill tokens based on the time elapsed since the last refill
    private void refill() {
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;

        // Calculate how many tokens to add based on the elapsed time and refill rate
        long tokensToAdd = (elapsedTime * refillRate) / TimeUnit.SECONDS.toNanos(1);

        if (tokensToAdd > 0) {
            currentTokens = Math.min(currentTokens + tokensToAdd, maxTokens);
            lastRefillTimestamp = now; // Reset last refill time to now
        }
    }
}
