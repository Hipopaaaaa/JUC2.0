package com.ohj.chapter6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author Hipopaaaaa
 * @create 2023/1/23 15:33
 * 体会AtomicMarkableReference
 */
public class AtomicMarkableReferenceDemo {

     static AtomicMarkableReference markableReference=new AtomicMarkableReference(100,false);

     public static void main(String[] args) {
          new Thread(()->{
               boolean marked = markableReference.isMarked();
               System.out.println(Thread.currentThread().getName()+"\t 默认标识:"+marked);
               //让t2线程拿到一样的标识
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
               markableReference.compareAndSet(100,1000,marked,!marked);
          },"t1").start();

          new Thread(()->{
               boolean marked = markableReference.isMarked();
               System.out.println(Thread.currentThread().getName()+"\t 默认标识:"+marked);
               //等待t1线程CAS完成
               try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
               boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);
               System.out.println(Thread.currentThread().getName()+"\t t2线程的CAS结果："+b);
               System.out.println(Thread.currentThread().getName()+"\t 当前的值和标记"+markableReference.getReference()+"\t "+markableReference.isMarked());
          },"t2").start();

     }
}
