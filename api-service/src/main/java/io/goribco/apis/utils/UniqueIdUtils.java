package io.goribco.apis.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class UniqueIdUtils {
    private static final Object lock = new Object();
    private static volatile UniqueIdUtils instance;
    private final int DEFAULT_SIZE;
    private final AtomicInteger mIdCounter = new AtomicInteger(0);
    private final AtomicReference<Long> mCurrentTime = new AtomicReference<>(System.currentTimeMillis());

    private UniqueIdUtils() {
        DEFAULT_SIZE = 11;
    }

    public static UniqueIdUtils on() {
        UniqueIdUtils r = instance;
        if (r == null) {
            synchronized (lock) {    // While we were waiting for the lock, another
                r = instance;        // thread may have instantiated the object.
                if (r == null) {
                    r = new UniqueIdUtils();
                    instance = r;
                }
            }
        }
        return r;
    }

    public String nextUUID() {
        return UUID.randomUUID().toString();
    }

    public String nextUUIDCompact() {
        return nextUUID().replace("-", "");
    }

    public int atomicIntegerCounter() {
        return mIdCounter.incrementAndGet();
    }

    public String getNanoId() {
        return on().getNanoId(DEFAULT_SIZE);
    }

    public String getNanoId(String prefix, int size) {
        return prefix + "X" + on().getNanoId(size);
    }

    public String getNanoId(final int size) {
        return new NanoIdUtils().randomNanoId(size);
    }

    public Long nextIdLong() {
        return mCurrentTime.accumulateAndGet(DateTimeUtil.getMilliSec(),
                (prev, next) -> next > prev ? next : prev + 1);
    }
}
