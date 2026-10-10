package service.user;

import java.util.concurrent.ConcurrentHashMap;

public class LoginRateLimiter {
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION_MS = 15 * 60 * 1000; // 15 minutes

    private static class AttemptRecord {
        int failedCount;
        long lastFailedTime;

        AttemptRecord(int failedCount, long lastFailedTime) {
            this.failedCount = failedCount;
            this.lastFailedTime = lastFailedTime;
        }
    }

    private static final ConcurrentHashMap<String, AttemptRecord> attemptsMap = new ConcurrentHashMap<>();

    public static boolean isBlocked(String key) {
        if (key == null) return false;
        AttemptRecord record = attemptsMap.get(key);
        if (record == null) {
            return false;
        }

        long now = System.currentTimeMillis();
        // Check if lockout period has expired
        if (now - record.lastFailedTime > LOCKOUT_DURATION_MS) {
            attemptsMap.remove(key);
            return false;
        }

        return record.failedCount >= MAX_FAILED_ATTEMPTS;
    }

    public static void recordFailedAttempt(String key) {
        if (key == null) return;
        long now = System.currentTimeMillis();
        attemptsMap.compute(key, (k, existing) -> {
            if (existing == null || (now - existing.lastFailedTime > LOCKOUT_DURATION_MS)) {
                return new AttemptRecord(1, now);
            } else {
                existing.failedCount++;
                existing.lastFailedTime = now;
                return existing;
            }
        });
    }

    public static void recordSuccess(String key) {
        if (key != null) {
            attemptsMap.remove(key);
        }
    }

    public static int getRemainingMinutes(String key) {
        AttemptRecord record = attemptsMap.get(key);
        if (record == null) return 0;
        long remainingMs = LOCKOUT_DURATION_MS - (System.currentTimeMillis() - record.lastFailedTime);
        return remainingMs > 0 ? (int) Math.ceil((double) remainingMs / 60000.0) : 0;
    }
}
