package com.ohj.chapter6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Hipopaaaaa
 * @create 2023/1/24 14:59
 * 需求：
 * 10个线程，每个线程转账1000
 * 不实用synchronized，使用AtomicIntegerFieldUpdater来实现
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(()->{
                try {
                    for (int j = 0; j < 1000; j++) {
                        bankAccount.add();
                        bankAccount.addAge();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t"+"result:"+bankAccount.money);
        System.out.println(Thread.currentThread().getName()+"\t"+"result:"+bankAccount.money);
    }
}

class BankAccount{
    String bankName="CCB";
    //更新对象必须用public volatile修饰
    public volatile  int money=0;

    //创建更新器，设置更新器要更新哪个类的哪个字段
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater=AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");

    public void add(){
        //传入具体实例对象，进行该对象字段的更新
        fieldUpdater.getAndIncrement(this);
    }

    public volatile  int age=0;
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater2=AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"age");
    public void addAge(){
        fieldUpdater2.getAndIncrement(this);
    }

}
