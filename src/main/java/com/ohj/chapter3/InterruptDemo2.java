package com.ohj.chapter3;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/18 15:23
 * 演示interrupt()不会导致线程中断
 */
public class InterruptDemo2 {
     public static void main(String[] args) {
          Thread t1 = new Thread(() -> {
               for (int i = 0; i < 300; i++) {
                    System.out.println("--- " + i);
               }
          }, "t1");
          t1.start();
          System.out.println("t1线程的默认中断标识: "+t1.isInterrupted()); //false
          try { TimeUnit.MILLISECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
          t1.interrupt();
          System.out.println("t1线程调用interrput()后的中断标识: "+t1.isInterrupted()); //true
     }
}
