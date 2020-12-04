package ee.uustal.ims.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LockServiceImpl implements LockService {

    private final Map<String, Lock> locks = new ConcurrentHashMap<>();

    public void lock(String username) {
        locks.computeIfAbsent(username, s -> new ReentrantLock()).lock();
    }

    public void unlock(String username) {
        final Lock lock = locks.get(username);
        if (lock == null) {
            throw new IllegalStateException();
        }
        lock.unlock();
    }

}
