package client;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GetMessageThread extends Thread{
    DatagramSocket ds;
    JTextArea ta;
    JComboBox cb;
    public GetMessageThread(ChatThreadWindow ctw){
        this.ds=ctw.ds;
        this.ta=ctw.ta;
        this.cb=ctw.cb;
    }
    public void run(){
        try{
            while(true){
                byte buff[]=new byte[1024];
                DatagramPacket dp=new DatagramPacket(buff,200);
                ds.receive(dp);
                String message=new String(buff,0,dp.getLength());
                ta.append(message);
                if(message.contains("进入了聊天室")){
                    message=message.replace("进入了聊天室","");
                    System.out.println("处理后的消息：" + message);
                }
                cb.addItem(message);
                System.out.println("UDP收的消息：" + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
