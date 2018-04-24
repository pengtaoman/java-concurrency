import java.util.concurrent.*;

public class RaceConditionExample {

    static class Counter {
    //class Counter {
        int count = 0;

        public void increment() {
            //System.out.println("!!!!!!! " + Thread.currentThread().getName() + "::" + this.getCount());
            count = count + 1;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //非静态内部类实例化
        //Counter counter = new RaceConditionExample().new Counter();

        //静态内部类实例化
        //Counter counter = new RaceConditionExample.Counter();
        Counter counter = new Counter();

        //() -> counter.increment() 的非简略写法
        Runnable ra =() -> {
            counter.increment();
        };

        for(int i = 0; i < 1000; i++) {
            //executorService.submit(() -> counter.increment());
            executorService.submit(ra);
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final count is : " + counter.getCount());
    }
}

