package com.ohj.chapter1;
import org.testng.annotations.Test;

import java.lang.reflect.Member;
import java.util.concurrent.*;

/**
 * @author Hipopaaaaa
 * @create 2023/1/16 20:20
 * CompeltableFuture的API使用
 */
public class CompletableFutureAPIDemo {


     @Test
     public static void method1() throws ExecutionException, InterruptedException, TimeoutException {
          CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
               try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return "abc";
          });
          //System.out.println(completableFuture.get());  //abc
          //System.out.println(completableFuture.get(2L,TimeUnit.SECONDS)); //抛出异常
          //System.out.println(completableFuture.join());  //abc
          //System.out.println(completableFuture.getNow("ohj"));  //ohj
          System.out.println(completableFuture.complete("Hipopaaaaa")+"\t"+completableFuture.get()); //true	Hipopaaaaa
     }

     @Test
     public static void method2(){
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
          CompletableFuture.supplyAsync(()->{
               System.out.println("第一步：");
               return 1;
          },threadPool).thenApply(f->{
               int i=10/0; //出现异常，第三不会执行，
               System.out.println("第二步：");
               return f+2;
          }).thenApply(f->{
               System.out.println("第三步：");
               return f+3;
          }).whenComplete((v,e)->{
               if(e==null){
                    System.out.println("计算的结果："+v);  //6
               }
          }).exceptionally(e->{
               e.printStackTrace();
               System.out.println(e.getMessage());
               return null;
          });
          System.out.println("主线程先去忙其他任务");
     }

     @Test
     public static void method3() throws ExecutionException, InterruptedException {
          CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
               return 1;
          }).thenApply(f -> {
               return f + 2;
          }).thenAccept(r -> {
               System.out.println(r); //3
          });
          System.out.println(future.get()); //null
          System.out.println(CompletableFuture.supplyAsync(()->"resultA").thenRun(()->{}).join()); //null
          System.out.println(CompletableFuture.supplyAsync(()->"resultA").thenAccept(r-> System.out.print(r+" ")).join()); // resultA null
          System.out.println(CompletableFuture.supplyAsync(()->"resultA").thenApply(r->r+"resultB").join()); //resultAresultB
     }

     @Test
     public static void method4() throws ExecutionException, InterruptedException, TimeoutException {
          ExecutorService threadPool = Executors.newFixedThreadPool(5);
          CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
               try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println("1号任务" + "\t" + Thread.currentThread().getName()); //pool-1-thread-1
               return "ohj";
          },threadPool).thenRunAsync(() -> {
               try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println("2号任务" + "\t" + Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
          }).thenRun(() -> {
               try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println("3号任务" + "\t" + Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
          }).thenRun(() -> {
               try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { throw new RuntimeException(e);}
               System.out.println("4号任务" + "\t" + Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
          });

          System.out.println(completableFuture.get(2L,TimeUnit.SECONDS));
     }

     @Test
     public static void method5() throws ExecutionException, InterruptedException {
          CompletableFuture<String> planA = CompletableFuture.supplyAsync(() -> {
               System.out.println("A come int");
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return "playA";
          });
          CompletableFuture<String> planB = CompletableFuture.supplyAsync(() -> {
               System.out.println("B come int");
               try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return "playB";
          });

          CompletableFuture<String> result = planA.applyToEither(planB, f -> {
               return f + " is winner";
          });
          System.out.println(result.get()); //playA is winner
     }

     @Test
     public static void method6() throws ExecutionException, InterruptedException {
          CompletableFuture<Integer> planA = CompletableFuture.supplyAsync(() -> {
               System.out.println(Thread.currentThread().getName() + "--启动");
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return 10;
          });
          CompletableFuture<Integer> planB = CompletableFuture.supplyAsync(() -> {
               System.out.println(Thread.currentThread().getName() + "--启动");
               try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
               return 20;
          });
          //合并结果
          CompletableFuture<Integer> result = planA.thenCombine(planB, (x, y) -> {
               System.out.println("---将两个结果合并");
               return x + y;
          });
          System.out.println(result.get()); //30
     }

     @Test
     public static void method7(){
          System.out.println(CompletableFuture.supplyAsync(() -> {
               System.out.println("---1");
               return 10;
          }).thenCombine(CompletableFuture.supplyAsync(() -> {
               System.out.println("---2");
               return 20;
          }), (x, y) -> {
               System.out.println("---3");
               return x + y;
          }).thenCombine(CompletableFuture.supplyAsync(() -> {
               System.out.println("---4");
               return 30;
          }), (x, y) -> {
               System.out.println("---5");
               return x + y;
          }).join());
     }
}
