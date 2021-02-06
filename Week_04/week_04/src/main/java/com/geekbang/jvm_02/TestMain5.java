package com.geekbang.jvm_02;

import java.util.concurrent.atomic.AtomicReference;

public class TestMain5 {
    public static void main(String args[]) throws InterruptedException {
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        new Thread(()->{
            atomicReference.set("太难了......");
        }).start();
        Thread.sleep(100);
        System.out.println("学无止境---->"+atomicReference.get());
    }
}
