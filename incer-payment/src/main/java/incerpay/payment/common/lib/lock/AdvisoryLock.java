package incerpay.payment.common.lib.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AdvisoryLock implements Lock {
    private final LockManager lockManager;
    private final String key;

    public AdvisoryLock(LockManager lockManager, String key) {
        this.lockManager = lockManager;
        this.key = key;
    }

    @Override
    public void lock() {
        this.lockManager.lock(key);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lockManager.lockInterruptibly(key);
    }

    @Override
    public boolean tryLock() {
        return this.lockManager.tryLock(key);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return this.lockManager.tryLock(key, time, unit);
    }

    @Override
    public void unlock() {
        this.lockManager.unlock(key);
    }

    @Override
    public Condition newCondition() {
        return this.lockManager.newCondition(key);
    }
}
