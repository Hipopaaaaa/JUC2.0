package com.ohj.chapter1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Hipopaaaaa
 * @create 2023/1/13 15:47
 * 演示FutureTask的缺点
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask=new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName()+"\t执行");
            TimeUnit.SECONDS.sleep(5); //停顿5秒
            return "task over";
        });
        new Thread(futureTask,"t1").start();

        //System.out.println(futureTask.get());  //这里会堵塞5秒,因为异步任务还没完成，get()等待结果
        System.out.println(Thread.currentThread().getName()+"\t 去做其他任务了..");

        //下面是获取结果的三种方式：
        //方式一： 把get()放在程序后，把获取结果放在这里，如果任务没结束还是会阻塞
        //System.out.println(futureTask.get()); //

        //方式二： 只等待2秒，若获取不到结果，直接抛出异常 但是也不优雅
        //System.out.println(futureTask.get(2,TimeUnit.SECONDS));

        //方式三： isDone()轮询，当异步任务处理完后，马上get()，不会出现阻塞
        while (true){
            if(futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                System.out.println("正在处理中.....");
            }
        }
    }
}
