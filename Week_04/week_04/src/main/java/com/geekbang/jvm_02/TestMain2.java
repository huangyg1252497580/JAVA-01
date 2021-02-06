package com.geekbang.jvm_02;

import java.util.concurrent.*;

public class TestMain2 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return getResult();
    }

    private String getResult() {
        return "大树底下不好乘凉";
    }

    /**
     * 使用线程池Future+Callable包装返回
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future = executorService.submit(new TestMain2());
        executorService.shutdown();
        System.out.println("返回值：" + future.get());
    }
}
