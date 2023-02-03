package com.ohj.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Hipopaaaaa
 * @create 2023/1/22 15:10
 * 实现一个自旋锁
 */
public class SpinLockDemo {
     //对线程进行原子引用，实现底层中只能有一个线程获取锁
     private AtomicReference<Thread> atomicReference=new AtomicReference<>();

     public void lock(){
          //获取当前线程，然后进行CAS操作
          Thread thread = Thread.currentThread();
          //预期值设为null表示内存中也是null时才能进行修改。
          while (!atomicReference.compareAndSet(null,thread)){
               System.out.println(thread.getName()+"正在自旋，尝试获取锁");
          }
          System.out.println(thread.getName()+"获取锁成功");
     }

     public void unLock(){
          Thread thread = Thread.currentThread();
          atomicReference.compareAndSet(thread,null);
          System.out.println(thread.getName()+"释放锁成功");
     }

     //线程A获取锁并持有锁100毫秒，线程B想获取锁，但只能通过自旋来等待，直到线程A释放锁。
     public static void main(String[] args) {
          SpinLockDemo spinLockDemo = new SpinLockDemo();
          new Thread(()->{
               spinLockDemo.lock();
               try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { throw new RuntimeException(e);}
               spinLockDemo.unLock();
          },"A").start();

          new Thread(()->{
               spinLockDemo.lock();
               spinLockDemo.unLock();
          },"B").start();

     }
}
