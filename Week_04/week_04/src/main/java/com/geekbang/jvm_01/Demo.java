package com.geekbang.jvm_01;

public class Demo {

    public static void main(String[] arg0) {
        System.out.println("main start=====");
        Thread thread1 = new Thread("守护线程"){
            @Override
            public void run() {
                int i = 0;
                while (i <= 4){
                    i++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"："+i);
                }
                super.run();
            }
        };


        Thread thread2 = new Thread("用户线程"){
            @Override
            public void run() {
                int i = 0;
                while (i <= 2){
                    i++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"："+i);
                }
                super.run();
            }
        };

        //setDaemon, 不设置则默认false
        thread1.setDaemon(true);//设置thread1为守护线程
        thread2.setDaemon(false);//设置thread2为普通线程

        thread1.start();
        thread2.start();

        System.out.println("main end==");
    }
}