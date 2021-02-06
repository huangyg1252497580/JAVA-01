package com.geekbang.jvm_02;

public class TestMain1 {

    private static String content = "默认值-->";
    public static String result = null;

    public static String getContent(String value) {
        return content + value;
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin............");
        new Thread(() -> {
            System.out.println("running............");
            result = TestMain1.getContent("来了老弟。");
        }).start();
        while (result == null) {
            Thread.sleep(100L);
        }
        System.out.println(TestMain1.result);
    }
}