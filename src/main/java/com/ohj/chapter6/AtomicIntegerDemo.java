package com.ohj.chapter6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hipopaaaaa
 * @create 2023/1/23 14:32
 * 基本类型原子类与CountDownLatch
 */
public class AtomicIntegerDemo {
     public static final int SIZE=50;
     private  static ExecutorService THREADPOOL= Executors.newFixedThreadPool(SIZE);

     public static void main(String[] args) throws InterruptedException {
          MyNumber myNumber = new MyNumber();
          CountDownLatch countDownLatch = new CountDownLatch(SIZE);
          for (int i = 0; i < SIZE; i++) {
               THREADPOOL.submit(()->{
                    try {
                         for (int j = 0; j < 1000000; j++) {
                              myNumber.add();
                         }
                    } finally {
                         countDownLatch.countDown();
                    }
               });
          }
          countDownLatch.await(); //阻塞
          System.out.println(Thread.currentThread().getName()+"\t"+"result: "+myNumber.atomicInteger.get()); //结果比50*1000000小
     }
}

class MyNumber{
     AtomicInteger atomicInteger=new AtomicInteger();

     public void add(){
          atomicInteger.getAndIncrement();
     }
}