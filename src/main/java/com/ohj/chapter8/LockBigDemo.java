package com.ohj.chapter8;

/**
 * @author Hipopaaaaa
 * @create 2023/1/31 16:23
 * 锁粗化
 */
public class LockBigDemo {
     static Object o = new Object();

     public void add(){
          synchronized (o){
               System.out.println("1111");
          }
          synchronized (o){
               System.out.println("2222");
          }
          synchronized (o){
               System.out.println("3333");
          }
          synchronized (o){
               System.out.println("4444");
          }
     }
}
