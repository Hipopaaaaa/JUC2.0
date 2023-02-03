package com.ohj.chapter7;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/26 14:27
 * 需求1： 5个销售卖房子，集团高层只关心销售总量的准确统计数。
 * 需求2： 5个销售卖完房子，各自独立销售额度，自己业绩按提成走，分灶吃饭。
 */
public class ThreadLocalDemo {
     public static void main(String[] args) {
          House house = new House();
          for (int i = 0; i < 5; i++) {
               new Thread(()->{
                    int size = new Random().nextInt(5) + 1;
                    try {
                         for (int j = 0; j < size; j++) {
                              house.saleHouse();
                              house.saleVolumeByThreadLocal();
                         }
                         System.out.println(Thread.currentThread().getName()+"\t 号销售卖出："+house.saleVolume.get());
                    } finally {
                         //使用完ThreadLocal后清空其值，避免内存溢出。
                         house.saleVolume.remove();
                    }
               },String.valueOf(i)).start();
          }

         try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { throw new RuntimeException(e);}
          System.out.println(Thread.currentThread().getName()+"\t 共计卖出："+house.saleCount);
     }
}

class House{
     int saleCount=0;
     public synchronized void saleHouse(){
          saleCount++;
     }
     //设置自己的销售额初始值为0
     ThreadLocal<Integer> saleVolume=ThreadLocal.withInitial(()->0);
     public void saleVolumeByThreadLocal(){
          //累加自己的销售额
          saleVolume.set(saleVolume.get()+1);
     }
}
