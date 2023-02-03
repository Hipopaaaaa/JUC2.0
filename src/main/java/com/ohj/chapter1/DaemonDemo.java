package com.ohj.chapter1;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/13 13:54
 * 测试守护线程和用户线程
 */
public class DaemonDemo {
     public static void main(String[] args) throws InterruptedException {
          Thread t1=new Thread(()->{
               System.out.println(Thread.currentThread().getName()+"\t开始运行，"+
                       (Thread.currentThread().isDaemon()?"守护线程":"用户线程"));
               while (true){

               }
          },"t1");

          //把t1线程变为守护线程
          //   当注释掉下面这行代码时，运行程序，程序不会自己结束，因为t1线程还在运行。
          //   当没注释掉时，运行程序，程序会自己结束，当main线程结束时，t1会自己结束。
          //t1.setDaemon(true);
          t1.start();
          TimeUnit.SECONDS.sleep(3);
          System.out.println(Thread.currentThread().getName()+"主线程结束----");
     }
}
