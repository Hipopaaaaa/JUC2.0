package com.ohj.chapter8;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/31 15:54
 */
public class SynchronizedUpDemo2 {
     public static void main(String[] args) {
          try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { throw new RuntimeException(e);}
          Object o = new Object();
          System.out.println("偏向锁状态：");
          System.out.println(ClassLayout.parseInstance(o).toPrintable());


          synchronized (o){
               o.hashCode(); //计算了hashcode后，对象立刻升级为重量级锁
               System.out.println(ClassLayout.parseInstance(o).toPrintable());
          }
     }
}
