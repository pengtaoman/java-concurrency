import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteCounter {
    ReadWriteLock lock = new ReentrantReadWriteLock();

    private int count = 0;

    /**
     * multiple threads can execute the getCount() method as long as no thread calls incrementAndGetCount().
     * If any thread calls incrementAndGetCount() method and acquires the write-lock,
     * then all the reader threads will pause their execution and wait for the writer thread to return.
     *
     *
     * */
    public int incrementAndGetCount() {
        lock.writeLock().lock();
        try {
            count = count + 1;
            return count;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}