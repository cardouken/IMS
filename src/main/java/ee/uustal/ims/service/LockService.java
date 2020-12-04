package ee.uustal.ims.service;

public interface LockService {

    void lock(String username);

    void unlock(String username);

}
