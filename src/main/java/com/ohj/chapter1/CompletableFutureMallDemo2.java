package com.ohj.chapter1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Hipopaaaaa
 * @create 2023/1/14 14:32
 * 体会join()
 * 方法
 */
public class CompletableFutureMallDemo2 {
     //get()方法需要抛出异常
     public void method1() throws ExecutionException, InterruptedException {
          CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
               return "ohj";
          });
          System.out.println(completableFuture.get());
     }

     //join()方法不需要抛异常
     public void method2()  {
          CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
               return "ohj";
          });
          System.out.println(completableFuture.join());
     }
}
