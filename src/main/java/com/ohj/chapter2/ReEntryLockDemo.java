package com.ohj.chapter2;

import org.testng.annotations.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hipopaaaaa
 * @create 2023/1/17 18:07
 * 可重入锁
 */
public class ReEntryLockDemo {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "\t ---中层调用");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "A").start();
    }

    @Test
    public void method1() {
        final Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t ---外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t ---中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t ---内层调用");
                    }
                }
            }
        }, "A").start();
    }
}
