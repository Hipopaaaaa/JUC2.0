package com.ohj.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/20 16:07
 * volatile没有原子性
 */
public class VolatileNoAtomicDemo {
     public static void main(String[] args) {
          MyNumber myNumber = new MyNumber();
          for (int i = 0; i < 10; i++) {
               new Thread(()->{
                    for (int j = 0; j < 1000; j++) {
                         myNumber.add();
                    }
               },String.valueOf(i)).start();
          }
          try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
          System.out.println(myNumber.number); //正确值应该是10000，但是volatile不具备原子性，因此会出现其他的值。要有原子性必须加锁。
     }
}

class MyNumber{
     public volatile int number;
     public  void add(){
          number++;
     }
}

class VolatileTest{
     int i=0;
     boolean flag=false;
     public void wirte(){
          i=2;
          flag=true;
     }
     public void read(){
          if(flag){
               System.out.println("i="+i);
          }
     }
}