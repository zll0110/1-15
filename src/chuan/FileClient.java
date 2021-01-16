package chuan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("192.168.1.21", 9999);
            FileOutputStream fos = new FileOutputStream("F:\\2021-01-15.rar");
            InputStream is = socket.getInputStream();
            byte buff[] = new byte[1024];
            int a = 0;
            while ((a = is.read(buff)) != -1) {
                fos.write(buff, 0, a);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
