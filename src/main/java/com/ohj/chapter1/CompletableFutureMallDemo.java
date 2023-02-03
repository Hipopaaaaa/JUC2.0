package com.ohj.chapter1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Hipopaaaaa
 * @create 2023/1/16 19:36
 *
 * 需求：
 *   同一款产品，同时搜索出同款产品在各大电商平台的售价
 *   同一款产品，同时搜索出本产品在同一个电商平台上，各个入驻卖家售价是多少
 *
 * 输出：
 *   同款产品在不同地方的价格清单列表，返回一个List
 */
public class CompletableFutureMallDemo {
     //电商平台
     static List<NetMall> list= Arrays.asList(
             new NetMall("jd"),
             new NetMall("taobao"),
             new NetMall("dangdang"),
             new NetMall("pdd"),
             new NetMall("tmall")
     );

     public static void main(String[] args) {
          long startTime = System.currentTimeMillis();
          //List<String> list = getPrice(CompletableFutureMallDemo.list, "mysql"); //耗时：3216 毫秒
          List<String> list = getPriceByCompletableFuture(CompletableFutureMallDemo.list, "mysql"); //耗时：1188 毫秒
          CompletableFutureMallDemo.list.forEach(System.out::println);
          long endTime = System.currentTimeMillis();
          System.out.println("耗时：" + (endTime - startTime) + " 毫秒");
     }
     //方法一： step by step 一家家搜索
     // List<NetMall> -----> map -----> List<String>
     public static List<String> getPrice(List<NetMall> list,String productName){
          //mysql in taobao price is 90.54
         return list.stream()
                  .map(netMall ->
                          String.format(productName+" in %s price is %.2f",
                                  netMall.getNetMallName(),
                                  netMall.calcPrice(productName)))
                  .collect(Collectors.toList());
     }

     //方法二：使用异步任务 一起搜索
     // List<NetMall> -----> List<CompletableFuture<String>> -----> List<String>
     public static List<String> getPriceByCompletableFuture(List<NetMall> list,String productName){
          return list.stream()
                  .map(netMall ->
                  CompletableFuture.supplyAsync(() -> String.format(productName + " in %s price is %.2f",
                          netMall.getNetMallName(),
                          netMall.calcPrice(productName)))).collect(Collectors.toList())
                  //第一次stream用map映射后，得到的List里，每一个元素都是一个CompetableFuture<String>异步任务，需要获取其结果，做第二次映射
                  .stream()
                  .map(s -> s.join())
                  .collect(Collectors.toList());
                  //第二次stream用map映射后，得到的List里，每个元素都是一个String
     }
}

//模拟电商网站
@AllArgsConstructor
@NoArgsConstructor
@Data
class NetMall{
     private String netMallName;

     //计算价格
     public double calcPrice(String productName){
          try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { throw new RuntimeException(e);}
          //返回一个价格
          return ThreadLocalRandom.current().nextDouble()*2+productName.charAt(0);
     }
}