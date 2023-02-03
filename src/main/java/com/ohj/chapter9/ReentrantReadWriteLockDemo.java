package com.ohj.chapter9;

import com.google.errorprone.annotations.Var;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Hipopaaaaa
 * @create 2023/2/2 15:17
 * 演示锁的演变
 */
public class ReentrantReadWriteLockDemo {
     public static void main(String[] args) {
          MyResource myResource = new MyResource();
          ExecutorService threadPool = Executors.newFixedThreadPool(30);
          for (int i = 0; i < 10; i++) {
               int finalI = i;
               threadPool.submit(()->{
                    myResource.write(finalI +"", finalI +"");
               });
          }
          for (int i = 0; i < 10; i++) {
               int finalI = i;
               threadPool.submit(()->{
                    myResource.read(finalI+"");
               });
          }
          threadPool.shutdown();
     }
}

class MyResource{
     Map<String,String> map=new HashMap();
     Lock lock=new ReentrantLock();
     ReadWriteLock rwLock=new ReentrantReadWriteLock();

     public void write(String key,String value){
          //lock.lock();
          rwLock.writeLock().lock();
          try {
               System.out.println(Thread.currentThread().getName()+"\t 正在写入");
               map.put(key,value);
               try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println(Thread.currentThread().getName()+"\t 完成写入");
          }finally {
               //lock.unlock();
               rwLock.writeLock().unlock();
          }
     }

     public void read(String key){
          //lock.lock();
          rwLock.readLock().lock();
          try {
               System.out.println(Thread.currentThread().getName()+"\t 正在读取");
               String result = map.get(key);
               try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println(Thread.currentThread().getName()+"\t 完成读取：\t"+result);
          }finally {
               //lock.unlock();
               rwLock.readLock().unlock();
          }
     }
}
