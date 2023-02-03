package com.ohj.chapter2;

/**
 * @author Hipopaaaaa
 * @create 2023/1/17 16:13
 */
public class LockSyncDemo {
     Object object =new Object();
     public void method1(){
          synchronized (object){
               System.out.println("-------hello");
          }
     }
}
