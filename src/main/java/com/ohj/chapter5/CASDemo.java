package com.ohj.chapter5;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hipopaaaaa
 * @create 2023/1/21 15:19
 */
public class CASDemo {
     public static void main(String[] args) {
          AtomicInteger atomicInteger = new AtomicInteger(5); //内存值为5
          System.out.println(atomicInteger.compareAndSet(5,2023)+"\t"+atomicInteger.get()); //预期值为5，更新值为2023 修改成功
          System.out.println(atomicInteger.compareAndSet(5,2024)+"\t"+atomicInteger.get()); //预期值为5，但内存值已经修改成2023，因此修改失败
          atomicInteger.getAndIncrement();

          AtomicInteger atomicInteger1 = new AtomicInteger(999);
          System.out.println(atomicInteger1.get());
          System.out.println(atomicInteger.get());
     }
}
