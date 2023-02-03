package com.ohj.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/20 15:26
 * 验证volatile的可见性
 * */
public class VolatileSeeDemo {

     //static  boolean flag=true;
     static  volatile  boolean flag=true;
     public static void main(String[] args) {
          new Thread(()->{
               System.out.println(Thread.currentThread().getName()+" ---come in");
               while (flag){

               }
               System.out.println(Thread.currentThread().getName()+" ---flag被设置为false程序停止");
          },"t1").start();

          try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}

          flag=false; //main线程修改了flag变量。
          System.out.println(Thread.currentThread().getName()+" 修改完成,flag:"+flag);
     }

}
