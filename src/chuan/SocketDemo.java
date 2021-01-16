package chuan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(8888);
            Socket socket=ss.accept();
            System.out.println("新用户进入系统");


            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os);
            PrintWriter out=new PrintWriter(osw);
            out.println("欢迎您xxx");
            out.flush();

            while(true) {
                InputStream is = socket
                        .getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String serverMessage = in.readLine();
                System.out.println("客户端说:" + serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
