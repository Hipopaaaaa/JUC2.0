package com.ohj.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hipopaaaaa
 * @create 2023/1/17 16:53
 */
public class SaleTicketDemo {
     public static void main(String[] args) {
          Ticket ticket = new Ticket();
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
          threadPool.execute(()->{
               for(int i=0;i<55;i++){
                    ticket.sale();
               }
          });

          threadPool.execute(()->{
               for(int i=0;i<55;i++){
                    ticket.sale();
               }
          });
          threadPool.execute(()->{
               for(int i=0;i<55;i++){
                    ticket.sale();
               }
          });
          threadPool.shutdown();
     }
}

class Ticket{
     private int num=50;
     ReentrantLock lock=new ReentrantLock(true);

     public void sale(){
          lock.lock();
          try {
               if(num>0){
                    System.out.println(Thread.currentThread().getName()+"卖出第:\t"+(num--)+"\t 还剩下："+num);
               }
          }finally {
               lock.unlock();
          }
     }
}
