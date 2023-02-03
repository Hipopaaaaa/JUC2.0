package com.ohj.chapter7;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Hipopaaaaa
 * @create 2023/1/27 04:40
 * 体会虚引用
 */
public class ReferenceDemo22 {
    public static void main(String[] args) {

        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference(new MyObject(), referenceQueue);
        //System.out.println(phantomReference.get());
        List<byte[]> list=new ArrayList();
        new Thread(()->{
            while (true){
                list.add(new byte[1*1024*1024]);
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { throw new RuntimeException(e);}
                System.out.println(phantomReference.get()+" list add ok");
            }
        },"t1").start();
        new Thread(()->{
            while (true){
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference!=null){
                    System.out.println("----有虚对象被回收，并加入了队列");
                    break;
                }
            }
        },"t2").start();


    }
}

class MyObject{
    @Override
    protected void finalize() throws Throwable {
        System.out.println("----invoke finalize method!");
    }
}