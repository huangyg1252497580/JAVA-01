package com.geekbang.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8802);
        while (true){
            try {
               final Socket socket = serverSocket.accept();
               new Thread(() ->{
                   server(socket);
               }).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void server(Socket socket){
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.println("hello,nio");
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
