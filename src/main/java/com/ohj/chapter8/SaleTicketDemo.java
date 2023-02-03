package com.ohj.chapter8;

/**
 * @author Hipopaaaaa
 * @create 2023/1/30 14:00
 * 演示偏向锁
 */
public class SaleTicketDemo {
     public static void main(String[] args) {
          Ticket ticket = new Ticket();
          new Thread(()->{for (int i=0;i<55;i++) ticket.sale();},"a").start();
          new Thread(()->{for (int i=0;i<55;i++) ticket.sale();},"b").start();
          new Thread(()->{for (int i=0;i<55;i++) ticket.sale();},"c").start();
     }
}
class Ticket{
     private int number=50;
     Object lockObject=new Object();
     public void sale(){
          synchronized (lockObject){
               if(number>0){
                    System.out.println(Thread.currentThread().getName()+"卖出第：\t"+(number--)+" 还剩下："+number);
               }
          }
     }
}