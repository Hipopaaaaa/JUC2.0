package com.ohj.chapter1;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * @author Hipopaaaaa
 * @create 2023/1/13 17:44
 * 演示CompletableFuture的四个静态方法
 */
public class CompletableFutureDemo {

     public static void main(String[] args) throws ExecutionException, InterruptedException {
          method4();
     }
     //无返回值，无线程池
     public static void method1() throws ExecutionException, InterruptedException {
          CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
               System.out.println(Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
               try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
          });
          System.out.println(future.get()); //null
     }

     //无返回值，有线程池
     public static void method2() throws ExecutionException, InterruptedException {
          //创建线程池
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
          CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
               System.out.println(Thread.currentThread().getName()); //pool-1-thread-1
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
          },threadPool);
          System.out.println(future.get()); //null
     }

     //有返回值，无线程池
     public static void method3() throws ExecutionException, InterruptedException {
          CompletableFuture<Object> future = CompletableFuture.supplyAsync(() ->{
               System.out.println(Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return "hello supplyAsync";
          });
          System.out.println(future.get()); //hello supplyAsync
     }

     //有返回值，有线程池
     public static void method4() throws ExecutionException, InterruptedException {
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
          CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
               System.out.println(Thread.currentThread().getName()); //pool-1-thread-1
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return "hello supplyAsync";
          }, threadPool);
          System.out.println(future.get()); //hello supplyAsync
     }
}
