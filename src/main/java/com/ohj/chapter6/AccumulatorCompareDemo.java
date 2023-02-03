package com.ohj.chapter6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Hipopaaaaa
 * @create 2023/1/24 15:57
 * 需求： 50个线程，每个线程1000w次，总点赞数出来
 */
public class AccumulatorCompareDemo {
    public static final int _10W = 100000;
    public static final int THREADNUMBER = 50;
    public static final ExecutorService threadPool = Executors.newFixedThreadPool(300);

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        method(clickNumber,"clickBySynchronized()", (r) -> clickNumber.clickBySynchronized(),
                (s) -> clickNumber.number);

        method(clickNumber,"clickByAtomicLong()", (r) -> clickNumber.clickByAtomicLong(),
                (s) -> clickNumber.atomicLong.get());

        method(clickNumber, "clickByLongAdder()",(r) -> clickNumber.clickByLongAdder(),
                (s) -> clickNumber.longAdder.sum());

        method(clickNumber,"clickByLongAccumulator()", (r) -> clickNumber.clickByLongAccumulator(),
                (s) -> clickNumber.longAccumulator.get());
        threadPool.shutdown();
    }

    public static void method(ClickNumber clickNumber,String methodName, Consumer consumer, Function<ClickNumber, Long> function) throws InterruptedException {
            CountDownLatch countDownLatch = new CountDownLatch(THREADNUMBER);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < THREADNUMBER; i++) {
                new Thread(()->{
                    try {
                        for (int j = 0; j < 100 * _10W; j++) {
                            consumer.accept(clickNumber);
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }).start();
            }
            countDownLatch.await();
            long endTime = System.currentTimeMillis();
            System.out.println(methodName+"耗时：" + (endTime - startTime) + " 毫秒,结果：" + function.apply(clickNumber));
    }

}

class ClickNumber {
    //方式一：
    long number = 0;
    public synchronized void clickBySynchronized() {
        number++;
    }

    //方式二：
    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    //方式三：
    LongAdder longAdder = new LongAdder();
    public void clickByLongAdder() {
        longAdder.increment();
    }

    //方式四：
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}
