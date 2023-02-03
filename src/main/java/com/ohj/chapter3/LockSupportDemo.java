package com.ohj.chapter3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hipopaaaaa
 * @create 2023/1/18 16:35
 *三种线程等待和唤醒的方式
 */
public class LockSupportDemo {

     public static void main(String[] args) {
          Thread t1 = new Thread(() -> {
               System.out.println(Thread.currentThread().getName() + "\t --come in");
               LockSupport.park(); //消费许可证，才会往下执行
               System.out.println(Thread.currentThread().getName() + "\t --被唤醒");
               LockSupport.park(); //消费许可证才会往下执行
               System.out.println(Thread.currentThread().getName() + "\t --再次被唤醒");
          }, "t1");
          t1.start();
          try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
         new Thread(()->{
              LockSupport.unpark(t1); //向t1线程发放permit 1
              System.out.println(Thread.currentThread().getName()+"\t --发出通知");
              //等待一下，让t1线程消费掉上一张许可证
              try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
              LockSupport.unpark(t1); //向t1线程再发permit
              System.out.println(Thread.currentThread().getName()+"\t --再次发出通知");

         },"t2").start();
     }

     private static void method2() {
          Lock lock = new ReentrantLock();
          Condition condition = lock.newCondition();
          new Thread(()->{
               lock.lock();
               try {
                    System.out.println(Thread.currentThread().getName()+"\t --come in");
                    condition.await();
                    System.out.println(Thread.currentThread().getName()+"\t --被唤醒");
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
               } finally {
                    lock.unlock();
               }
          },"t1").start();
          try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
          new Thread(()->{
               lock.lock();
               try {
                    condition.signal();
                    System.out.println(Thread.currentThread().getName()+"\t --发出通知");
               }finally {
                    lock.unlock();
               }
          },"t2").start();
     }

     private static void method1() {
          Object objectLock = new Object();

          new Thread(()->{
               synchronized (objectLock){
                    System.out.println(Thread.currentThread().getName()+"\t --come in");
                    try {
                         objectLock.wait();
                    } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+"\t --被唤醒");
               }
          },"t1").start();
          try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}

          new Thread(()->{
               synchronized (objectLock){
                    objectLock.notify();
                    System.out.println(Thread.currentThread().getName()+"\t --发出通知");
               }
          },"t2").start();
     }
}
