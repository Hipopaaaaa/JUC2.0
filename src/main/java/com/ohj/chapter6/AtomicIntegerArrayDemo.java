package com.ohj.chapter6;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Hipopaaaaa
 * @create 2023/1/23 15:14
 * 数组类型原子类的使用
 */
public class AtomicIntegerArrayDemo {
     public static void main(String[] args) {
          AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
          //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5;

          //遍历
          for (int i = 0; i < atomicIntegerArray.length(); i++) {
               System.out.println(atomicIntegerArray.get(i));
          }

          int tmp = atomicIntegerArray.getAndSet(0, 1122);
          System.out.println(tmp+"\t"+atomicIntegerArray.get(0)); //0 1122

          int tmp2 = atomicIntegerArray.getAndIncrement(0);
          System.out.println(tmp2+"\t"+atomicIntegerArray.get(0));  //1122  1123
     }
}
