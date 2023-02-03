package com.ohj.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Hipopaaaaa
 * @create 2023/1/22 16:30
 * ABA问题以及解决方法
 */
public class ABADemo {
     static AtomicInteger atomicInteger=new AtomicInteger(100);
     static AtomicStampedReference<Integer> stampedReference=new AtomicStampedReference<>(100,1);

     public static void main(String[] args) {

          //t1线程发生ABA问题
          new Thread(()->{
               int stamp = stampedReference.getStamp();
               System.out.println(Thread.currentThread().getName()+"\t 首次版本号："+stamp); //1
               //暂停500毫秒，保证t2线程初始化拿到的版本号和t1一样
               try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { throw new RuntimeException(e);}
               stampedReference.compareAndSet(100,101,stampedReference.getStamp(),stampedReference.getStamp()+1);
               System.out.println(Thread.currentThread().getName()+"\t 2次版本号："+stampedReference.getStamp());//2

               stampedReference.compareAndSet(101,100,stampedReference.getStamp(),stampedReference.getStamp()+1);
               System.out.println(Thread.currentThread().getName()+"\t 3次版本号："+stampedReference.getStamp());//3
          },"t1").start();

          new Thread(()->{
               int stamp = stampedReference.getStamp();
               System.out.println(Thread.currentThread().getName()+"\t 首次版本号："+stamp);//1
               //暂停1秒钟，等待t1线程发生ABA问题
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}

               boolean b = stampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
               System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());//false 100 3
          },"t2").start();
     }

     private static void abaHappen() {
          //t1线程发生ABA
          new Thread(()->{
               atomicInteger.compareAndSet(100,101);
               try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { throw new RuntimeException(e);}
               atomicInteger.compareAndSet(101,100);
          },"t1").start();

          new Thread(()->{
               try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println(atomicInteger.compareAndSet(100,2023)+"\t"+atomicInteger.get()); //true 2023
          },"t2").start();
     }
}
