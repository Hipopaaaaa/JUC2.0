package com.ohj.chapter9;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author Hipopaaaaa
 * @create 2023/2/2 16:59
 * 演示邮戳锁
 */
public class StampedLockDemo {
    public static void main(String[] args) {
        MyResource2 myResource2 = new MyResource2();
        //for (int i = 0; i < 10; i++) {
        //    new Thread(() -> {
        //        myResource2.read();
        //    }, String.valueOf(i)).start();
        //}
        //for (int i = 0; i < 10; i++) {
        //    new Thread(() -> {
        //        myResource2.write();
        //    }, String.valueOf(i)).start();
        //}

        new Thread(()->{
            myResource2.tryOptimisticRead();
        },"读线程1").start();
        //若把睡眠时间改为4秒以内，则第一个读线程在读的时候，写线程修改了值，它会重新读
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { throw new RuntimeException(e);}
        new Thread(()->{
            myResource2.write();
        },"写线程").start();
        new Thread(()->{
            myResource2.tryOptimisticRead();
        },"读线程2").start();

    }
}

class MyResource2 {
    public static int number = 100;
    //创建邮戳锁
    public static StampedLock stampedLock = new StampedLock();

    public void write() {
        //加写锁,获得戳记
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t 正在写入");
        try {
            number = number + 10;
        } finally {
            //锁放锁，传入戳记
            stampedLock.unlock(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t 完成写入");
    }

    //悲观读，读没有完成时，写锁无法获取锁
    public void read() {
        //加悲观读锁,获得戳记
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t 正在悲观读取：");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(Thread.currentThread().getName() + "\t 完成悲观读取：" + number);
        } finally {
            //释放读锁
            stampedLock.unlock(stamp);
        }
    }

    //乐观读，读读过程中允许写锁获取锁
    public void tryOptimisticRead() {
        //加乐观读锁，获得戳记
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //故意间隔4秒，很乐观地认为读取中没有其他线程修改number值
        // stampedLock.validate(stamp) 根据戳记判断读取过程中，有没有写线程获取到锁，有则返回false；无则返回true
        System.out.println("4秒前 stampedLock.validate(stamp) 值：" + stampedLock.validate(stamp));
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { throw new RuntimeException(e);}
        System.out.println("4秒后 stampedLock.validate(stamp) 值：" + stampedLock.validate(stamp));

        //若被修改，则需要进行重新读取
        if (!stampedLock.validate(stamp)) {
            System.out.println("有线程进行修改了...");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级 悲观读");
                result=number;
                System.out.println("重新悲观读后的值:"+result);
            }finally {
                stampedLock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName()+"\t 最后的值："+result);
    }

}
