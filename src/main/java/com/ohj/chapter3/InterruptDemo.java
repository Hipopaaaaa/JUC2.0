package com.ohj.chapter3;

import com.google.errorprone.annotations.Var;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Hipopaaaaa
 * @create 2023/1/18 14:45
 * 线程中断的三种方式
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean=new AtomicBoolean(false);
    public static void main(String[] args) {
         Thread t1 = new Thread(() -> {
              while (true){
                   if(Thread.currentThread().isInterrupted()){ //检测中断标识位
                        System.out.println(Thread.currentThread().getName()+"\t isInterrputed()被修改为true，程序停止");
                        break;
                   }
                   System.out.println("---hello interrupt api");
              }
         }, "t1");
         t1.start();
         try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) { throw new RuntimeException(e);}
         new Thread(()->{
              t1.interrupt();  //修改t1的中断标识位为true，中断t1线程
         },"t2").start();
    }

     private static void method2() {
          new Thread(()->{
               while (true){
                    if(atomicBoolean.get()){
                         System.out.println(Thread.currentThread().getName()+"\t atomicBoolean被修改为true，程序停止");
                         break;
                    }
                    System.out.println("---hello volatile");
               }
          },"t1").start();
          try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { throw new RuntimeException(e);}
          new Thread(()->{
               atomicBoolean.set(true); //修改为true，停止t1线程
          },"t2").start();
     }

     private static void method1() {
          new Thread(()->{
               while (true){
                    if(isStop){
                         System.out.println(Thread.currentThread().getName()+"\t isStop被修改为true，程序停止");
                         break;
                    }
                    System.out.println("---hello volatile");
               }
          },"t1").start();
          try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { throw new RuntimeException(e);}
          new Thread(()->{
               isStop=true; //修改为true后，t1线程停止
          },"t2").start();
     }
}
