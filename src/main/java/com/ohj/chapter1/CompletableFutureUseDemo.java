package com.ohj.chapter1;

import java.util.concurrent.*;

/**
 * @author Hipopaaaaa
 * @create 2023/1/14 13:35
 * CompletableFuture的使用
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //创建异步任务
        CompletableFuture<Integer> completableFuture=CompletableFuture.supplyAsync(()->{
            int result = ThreadLocalRandom.current().nextInt(10);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
            System.out.println("----1秒后出结果: "+result);
            //模拟出异常
            //if(result>5){
            //    int i=10/0;
            //}
            return result;
        },threadPool).whenComplete((v,e)->{
            //whenComplete(v,e)是异步任务执行完后自动调用，v 是返回值， e 是异常
            if(e==null){
                System.out.println("---计算完成，没有异常发生,更新系统updateValue"+v);
                System.out.println(Thread.currentThread().getName());
            }
        }).exceptionally(e->{
            //exceptionally(e)是异步任务出异常后会执行
            e.printStackTrace();
            System.out.println("异常情况："+e.getCause()+"\t"+e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");

        //若没指明线程池，主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        //try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { throw new RuntimeException(e);}
        //System.out.println(completableFuture.get());

        threadPool.shutdown();
    }
}
