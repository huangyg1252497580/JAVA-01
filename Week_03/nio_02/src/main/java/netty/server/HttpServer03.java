package netty.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer03 {

    public static void main(String args[]) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8803);
        while (true){
            try {
                final Socket socket = serverSocket.accept();
                executorService.execute(() ->server(socket));

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
            System.out.println("访问进来了。。。。。。。。。。。。。。。。。");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
