package com.ohj.chapter2;

import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/17 14:52
 * 8锁案例解释对象锁和类锁
 * 题目： 对多线程锁对理解，8锁案例说明：
 *
 * 8锁案例说明：
 * 1 标准访问有ab两个线程，请问先打印邮件还是短信？  邮件
 * 2 sendEmail方法中加入暂停3秒，请问先打印邮件还是短信？  邮件
 * 3 添加一个普通对hello方法，请问先打印邮件还是hello？  hello
 * 4 有两部手机，请问先打印邮件还是短信？  短信
 * 5 有两个静态同步方法，有1部手机，请问先打印邮件还是短信？ 邮件
 * 6 有两个静态同步方法，有2部手机，请问先打印邮件还是短信？ 邮件
 * 7 有一个静态同步方法（邮件），有一个普通同步方法（短信），有1部手机，请问先打印邮件还是短信？  短信
 * 8 有一个静态同步方法（邮件），有一个普通同步方法（短信），有2部手机，请问先打印邮件还是短信？  短信
 */

public class Lock8Demo {
     public static void main(String[] args) {
          Phone phone = new Phone();
          Phone phone2 = new Phone();
          new Thread(()->{
               phone.sendEmail();
          },"a").start();
          //保证a线程先启动
          try { TimeUnit.MILLISECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}

          new Thread(()->{
               //phone.sendSMS();
               //phone.hello();
               phone2.sendSMS();
          },"b").start();
     }
}

class Phone{   //资源类
     public static synchronized  void sendEmail(){
          try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { throw new RuntimeException(e);}
          System.out.println("----sendEmail");
     }
     public  synchronized void sendSMS() {
          System.out.println("----sendSMS");
     }
     public void hello(){
          System.out.println("-----hello");
     }
}
