package com.ohj.chapter7;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hipopaaaaa
 * @create 2023/1/26 14:59
 * 必须回收自定义的ThreadLocal变量，尤其在线程池场景下，线程经常会被复用，
 * 若不清理自定义的ThreadLocal变量，可能会影响后续业务逻辑和造成内存泄漏问题。
 */
public class ThreadLocalDemo2 {
     public static void main(String[] args) {
          MyData myData = new MyData();
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
          try {
               //开启10个线程，每个线程中的值为1
               for (int i = 0; i < 10; i++) {
                    threadPool.submit(()->{
                         try {
                              Integer beforeInt = myData.threadLocalField.get();
                              myData.add();
                              Integer afterInt = myData.threadLocalField.get();
                              System.out.println(Thread.currentThread().getName()+"\t before："+beforeInt+"\t after："+afterInt);
                         } finally {
                              myData.threadLocalField.remove();
                         }
                    });
               }
          }catch (Exception e){
                throw new RuntimeException(e);
          }finally {
               threadPool.shutdown();
          }

     }
}

class MyData{
     ThreadLocal<Integer> threadLocalField=ThreadLocal.withInitial(()->0);
     public void add(){
          threadLocalField.set(threadLocalField.get()+1);
     }
}
