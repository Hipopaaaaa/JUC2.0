package com.ohj.chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Hipopaaaaa
 * @create 2023/1/13 14:27
 * 演示FutureTask的使用
 */
public class FutureDemo {
     public static void main(String[] args) throws ExecutionException, InterruptedException {
          //创建异步任务
          FutureTask<String> futureTask=new FutureTask(new MyThread2());
          Thread t1=new Thread(futureTask,"t1");
          t1.start();
          //获取执行异步任务的结果
          System.out.println(futureTask.get());
     }
}

class MyThread2 implements Callable<String>{
     @Override
     public String call() throws Exception {
          System.out.println("----come int call()----");
          return "hell Callable";
     }
}
