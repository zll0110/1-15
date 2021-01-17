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
                if(message.contains("������������")){
                    message=message.replace("������������","");
                    System.out.println("��������Ϣ��" + message);
                }
                cb.addItem(message);
                System.out.println("UDP�յ���Ϣ��" + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
