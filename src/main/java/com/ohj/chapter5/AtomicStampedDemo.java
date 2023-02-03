package com.ohj.chapter5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Hipopaaaaa
 * @create 2023/1/22 16:07
 * AtomicStampedReference的API使用
 */
public class AtomicStampedDemo {
     public static void main(String[] args) {
          Book javaBook = new Book(1, "java");
          //初始化内存值为javaBook对象，版本号为1
          AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook, 1);
          System.out.println("\t\t"+atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());//获取当前内容 和 当前版本号

          Book mysqlBook = new Book(2, "mysql");
          //更新内存值为mysqlBook对象
          //参数1-预期值A  参数2-更新值B 参数3-预期版本号 参数4-更新版本号
          boolean b = atomicStampedReference.compareAndSet(javaBook, mysqlBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
          System.out.println(b+"\t"+atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());

          //把内存值的mysqlBook对象改回javaBook对象
          b = atomicStampedReference.compareAndSet(mysqlBook, javaBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
          System.out.println(b+"\t"+atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());

     }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Book{
     private int id;
     private String name;
}
