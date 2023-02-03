package com.ohj.chapter6;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Hipopaaaaa
 * @create 2023/1/24 15:47
 * 原子增强类的使用
 */
public class LongAdderAPIDemo {
     public static void main(String[] args) {
          LongAdder longAdder = new LongAdder();
          longAdder.increment();
          longAdder.increment();
          longAdder.increment();
          System.out.println(longAdder.sum()); //3

          //自定义进行乘法运算
          LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 1);
          longAccumulator.accumulate(2);
          longAccumulator.accumulate(4);

          System.out.println(longAccumulator.get()); //8
     }
}
