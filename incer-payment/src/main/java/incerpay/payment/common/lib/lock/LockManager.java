package incerpay.payment.common.lib.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public interface LockManager {
    Lock getLock(String key);

    void lock(String key);

    void lockInterruptibly(String key) throws InterruptedException;

    boolean tryLock(String key);

    boolean tryLock(String key, long time, TimeUnit unit) throws InterruptedException;

    Condition newCondition(String key);

    void unlock(String key);
}
