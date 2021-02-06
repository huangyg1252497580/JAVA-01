package com.geekbang.jvm_01;

public class DaemonDemo {

    public static void main(String args[]){
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Thread thread = Thread.currentThread();
                System.out.println("当前线程名称:"+thread.getName());
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName("test-thread-1");
        thread.setDaemon(true);
        thread.start();

    }
}
