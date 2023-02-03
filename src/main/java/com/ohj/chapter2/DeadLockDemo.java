package com.ohj.chapter2;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/17 18:40
 * 死锁案例
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final Object A=new Object();
        final Object B=new Object();
        new Thread(()->{
            synchronized (A){
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
                System.out.println(Thread.currentThread().getName()+"\t 持有A锁，希望获得B锁");
                synchronized (B){
                    System.out.println(Thread.currentThread().getName()+"\t 获取到B锁");
                }
            }
        },"线程A").start();

        new Thread(()->{
            synchronized (B){
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
                System.out.println(Thread.currentThread().getName()+"\t 持有B锁，希望获得A锁");
                synchronized (A){
                    System.out.println(Thread.currentThread().getName()+"\t 获取到A锁");
                }
            }
        },"线程B").start();
    }
}
