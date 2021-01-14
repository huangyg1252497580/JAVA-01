package com.geekbang.jvm;

public class ByteCodeTest {

   public static void  main(String args[]){

       int a = 0;
       int b = 3;
       int c = 5;

       c = c * (a + b);

       System.out.println("result--:"+c);
       System.out.println(Long.MAX_VALUE);

   }
}
