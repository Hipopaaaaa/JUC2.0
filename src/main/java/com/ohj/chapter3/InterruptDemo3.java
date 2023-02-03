package com.ohj.chapter3;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/18 15:35
 * 线程处于阻塞状态时，调用interrupt()会抛出异常。
 */
public class InterruptDemo3 {
     public static void main(String[] args) {
          Thread t1 = new Thread(() -> {
               while (true){
                    if(Thread.currentThread().isInterrupted()){
                         System.out.println(Thread.currentThread().getName()+
                                 " 中断标识位："+Thread.currentThread().isInterrupted()+"程序停止");
                         break;
                    }
                    //阻塞一下
                    try {
                         Thread.sleep(200);
                    } catch (InterruptedException e) {
                         //为什么要在异常处再调用一次？
                         // 分析： t2线程把t1线程的中断标识位设置true，但遇到t1线程阻塞，t1抛出异常InterruptedException，并把t1中断状态清除
                         //       t1的中断状态又变回false。
                         Thread.currentThread().isInterrupted();
                         e.printStackTrace();
                    }
                    System.out.println("---hello t1");
               }
          }, "t1");
          t1.start();
          try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
          new Thread(()-> t1.interrupt(),"t2").start();
     }
}
