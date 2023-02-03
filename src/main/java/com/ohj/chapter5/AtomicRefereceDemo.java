package com.ohj.chapter5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Hipopaaaaa
 * @create 2023/1/22 14:44
 * 原子引用
 */
public class AtomicRefereceDemo {
     public static void main(String[] args) {
          AtomicReference<User> userAtomicReference = new AtomicReference<>();
          User user = new User("zhangsan", 45);
          User user2 = new User("ohj", 23);
          userAtomicReference.set(user); //设置内存值
          //比较并交换 -预期值是user对象，更新值是user2对象
          System.out.println(userAtomicReference.compareAndSet(user,user2)+"\t"+userAtomicReference.get().toString()); //修改成功
          System.out.println(userAtomicReference.compareAndSet(user,user2)+"\t"+userAtomicReference.get().toString()); //修改失败
     }
}

@Data
@AllArgsConstructor
@ToString
class User{
     private String name;
     private int age;
}
