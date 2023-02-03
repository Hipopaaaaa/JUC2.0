package com.ohj.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author Hipopaaaaa
 * @create 2023/1/24 15:13
 * 需求：
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作
 * 要求只能被初始化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdateDemo {
     public static void main(String[] args) {
          ExecutorService threadPool = Executors.newFixedThreadPool(10);
          Myvar myvar = new Myvar();
          for (int i = 0; i < 10; i++) {
               threadPool.submit(()->{
                    myvar.init(myvar);
               });
          }
     }
}

class Myvar{
     public volatile  Boolean isInit=Boolean.FALSE;

     //参数1-哪个类 参数2-属性属于哪个引用类型 参数3-哪个属性
     AtomicReferenceFieldUpdater<Myvar,Boolean> atomicReferenceFieldUpdater=
             AtomicReferenceFieldUpdater.newUpdater(Myvar.class,Boolean.class,"isInit");

     public void init(Myvar myvar){
          if(atomicReferenceFieldUpdater.compareAndSet(myvar,Boolean.FALSE,Boolean.TRUE)){
               System.out.println(Thread.currentThread().getName()+":\t 开始初始化");
               try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println(Thread.currentThread().getName()+":\t 结束初始化");
          }else {
               System.out.println(Thread.currentThread().getName()+":\t 已经有线程在进行初始化");
          }
     }

}
