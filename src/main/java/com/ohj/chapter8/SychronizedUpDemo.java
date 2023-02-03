package com.ohj.chapter8;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Hipopaaaaa
 * @create 2023/1/28 15:49
 * 无锁时，对象头的信息
 */
public class SychronizedUpDemo {
     public static void main(String[] args) {
          Object o = new Object();
          System.out.println("16进制"+Integer.toHexString(o.hashCode()));
          System.out.println("2进制"+Integer.toBinaryString(o.hashCode()));
          System.out.println(ClassLayout.parseInstance(o).toPrintable());
     }
}
