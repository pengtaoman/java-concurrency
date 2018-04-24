import java.util.concurrent.*;

class SynchronizedCounter {
    private int count = 0;

    public SynchronizedCounter() {
        System.out.println(" !!!!!!!!! SynchronizedCounter init!!!!!!!");
    }

    // Synchronized Method

    // Note that the concept of Synchronization is always bound to an object.
    // In the above case, multiple invocations of increment() method on the same object
    // is not possible, but threads can safely call increment() method
    // on different objects at the same time, and that will not result into a race condition.
    // 不太懂
    public synchronized void increment() {
        //System.out.println("!!!!!!! " + Thread.currentThread().getName() + "::" + this.getCount());
        count = count + 1;
    }

    public int getCount() {
        return count;
    }
}

public class SynchronizedMethodExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> synchronizedCounter.increment());
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final count is : " + synchronizedCounter.getCount());
    }
}