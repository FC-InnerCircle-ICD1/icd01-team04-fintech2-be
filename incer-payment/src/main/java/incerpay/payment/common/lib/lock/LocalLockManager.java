package incerpay.payment.common.lib.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LocalLockManager implements LockManager {
    private static final Lock CREATE_LOCK = new ReentrantLock();
    private final Map<String, Lock> realLocks;
    private final Map<String, AdvisoryLock> advisoryLocks;

    public LocalLockManager(){
        this.realLocks = new ConcurrentHashMap<>();
        this.advisoryLocks = new ConcurrentHashMap<>();
    }

    @Override
    public Lock getLock(String key) {
        CREATE_LOCK.lock();
        try{
            realLocks.computeIfAbsent(key, k -> new ReentrantLock());
            return advisoryLocks.computeIfAbsent(key, k -> new AdvisoryLock(this, k));
        } finally {
            CREATE_LOCK.unlock();
        }
    }

    @Override
    public void lock(String key) {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        this.realLocks.get(key).lock();
    }

    @Override
    public void lockInterruptibly(String key) throws InterruptedException {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        this.realLocks.get(key).lockInterruptibly();
    }

    @Override
    public boolean tryLock(String key) {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        return this.realLocks.get(key).tryLock();
    }

    @Override
    public boolean tryLock(String key, long time, TimeUnit unit) throws InterruptedException {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        return this.realLocks.get(key).tryLock(time, unit);
    }

    @Override
    public Condition newCondition(String key) {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        return this.realLocks.get(key).newCondition();
    }

    @Override
    public void unlock(String key) {
        if(!this.realLocks.containsKey(key)){
            throw new LockManagerException("Lock not found for key: " + key);
        }
        this.realLocks.get(key).unlock();
    }

}
