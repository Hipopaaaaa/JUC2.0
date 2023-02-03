package com.ohj.chapter8;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/30 15:53
 * 演示偏向锁
 */
public class SynchronizedUpDemo {
     public static void main(String[] args) {

          //等待偏向锁开启，没有这一行，则o是无锁状态 001
          try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { throw new RuntimeException(e);}
          Object o = new Object();
          //println的源码中有锁，且只有main线程访问，因此o升级为偏向锁 101
          System.out.println(ClassLayout.parseInstance(o).toPrintable());
          System.out.println("==============================");
          new Thread(()->{
               synchronized (o){
                    System.out.println(ClassLayout.parseInstance(o).toPrintable());
               }
          }).start();
     }
}
