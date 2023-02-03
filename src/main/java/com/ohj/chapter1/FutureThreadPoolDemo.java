package com.ohj.chapter1;

import org.junit.jupiter.api.Test;

import javax.lang.model.element.VariableElement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/13 15:12
 * 演示FutureTask的优点
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        //3个任务，目前开启多个异步任务线程来处理,耗时：140 毫秒
        //创建有3个线程的线程池。避免因为开3个任务，new3次Thread对象。
        long startTime = System.currentTimeMillis();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask1=new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return "task1 over";
        });
        threadPool.submit(futureTask1);
        FutureTask<String> futureTask2=new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(300);
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        FutureTask<String> futureTask3=new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(300);
            return "task3 over";
        });
        threadPool.submit(futureTask3);

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + " 毫秒");
        threadPool.shutdown();
    }
    @Test
    public void m1() throws InterruptedException {
        //3个任务，目前只有一个线程来处理 耗时：1111 毫秒
        long startTime = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MILLISECONDS.sleep(300);
        TimeUnit.MILLISECONDS.sleep(300);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + " 毫秒");
    }
}
