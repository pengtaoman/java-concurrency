import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockMethodsCounter {
    private final ReentrantLock lock = new ReentrantLock();

    private int count = 0;

    public int incrementAndGet() {
        // Check if the lock is currently acquired by any thread
        System.out.println("IsLocked : " + lock.isLocked());

        // Check if the lock is acquired by the current thread itself.
        System.out.println("IsHeldByCurrentThread : " + lock.isHeldByCurrentThread()
                + " ;; CurrentThread::" + Thread.currentThread().getName());

        // Try to acquire the lock  thread2 在这里试着取锁，发现已被锁，则直接返回了，因此count为0
        /**
         * The tryLock() method tries to acquire the lock without pausing the thread.
         * That is, If the thread couldn’t acquire the lock because it was held by some other thread,
         * then It returns immediately instead of waiting for the lock to be released.
         * */
        boolean isAcquired = lock.tryLock();
//        boolean isAcquired = false;
//        try {
//            isAcquired = lock.tryLock(3, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("Lock Acquired : " + isAcquired + "\n");

        if(isAcquired) {
            try {
                Thread.sleep(2000);
                count = count + 1;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        }
        return count;
    }
}

public class ReentrantLockMethodsExample {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLockMethodsCounter lockMethodsCounter = new ReentrantLockMethodsCounter();

        executorService.submit(() -> {

            System.out.println("IncrementCount (First Thread) : " + Thread.currentThread().getName() + " -- " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.submit(() -> {
            System.out.println("IncrementCount (Second Thread) : "  + Thread.currentThread().getName() + " -- " +
                    lockMethodsCounter.incrementAndGet() + "\n");
        });

        executorService.shutdown();
    }
}