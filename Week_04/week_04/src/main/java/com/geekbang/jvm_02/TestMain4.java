package com.geekbang.jvm_02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain4 {

    private static int result;
    public static void main(String args[]){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("来了老弟...........");
                result = getSum();
            }
        });
        executorService.shutdown();
        System.out.println("你要的结果来了->"+result);
    }

    public static int getSum(){
        int i = 0;
        while (i <10)
            i++;
        return i;
    }
}
