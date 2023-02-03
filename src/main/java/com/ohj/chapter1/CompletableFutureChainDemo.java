package com.ohj.chapter1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Hipopaaaaa
 * @create 2023/1/14 14:24
 * 体会链式语法
 */
public class CompletableFutureChainDemo {
     public static void main(String[] args) {
          Student student = new Student();
          //以前的赋值方式
          student.setId(1);
          student.setName("ohj");
          student.setMajor("math");

          //链式语法
          student.setId(1).setName("ohj").setMajor("math");
     }
}

@Accessors(chain = true) //开启链式语法
@NoArgsConstructor
@AllArgsConstructor
@Data
class Student{
     private Integer id;
     private String name;
     private String major;
}
