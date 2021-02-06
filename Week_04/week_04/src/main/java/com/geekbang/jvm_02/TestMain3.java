package com.geekbang.jvm_02;

import java.util.concurrent.CompletableFuture;

public class TestMain3 {

    public static void main(String[] args) {
        String result = CompletableFuture.supplyAsync(() ->
                " 111111111111 -> "
        ).thenApplyAsync(v ->
                v + " 22222222222222 -> "
        ).join();
        System.out.println("返回值：" + result);
    }
}
