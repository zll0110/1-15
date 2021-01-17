package client;

import org.omg.CORBA.portable.UnknownException;
import sun.nio.ch.DatagramSocketAdaptor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.transform.Result;

/*
 * �����߳�
 */
public class ChatThreadWindow {
    private String name;
    JComboBox cb;  //��Ͽ�
    private JFrame f;
    JTextArea ta;
    private JTextField tf;  //��Ϣ��
    private static int total;// ��������ͳ��
    DatagramSocket ds;

    public  ChatThreadWindow (String name,DatagramSocket ds){
        /*
         * ���������Ҵ��ڽ���
         */
        this.ds=ds;
        this.name=name;
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 400);
        f.setTitle("������" + " - " + name + "     ��ǰ��������:" + ++total);
        f.setLocation(300, 200);
        ta = new JTextArea();
        JScrollPane sp = new JScrollPane(ta);
        ta.setEditable(false);
        tf = new JTextField();
        tf.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                        //System.out.println("ѡ��"+cb.getSelectedItem());
                        String messageTo = (String) cb.getSelectedItem();
                        if ("All".equals(messageTo)) {
                            //
                        } else {
                            //
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            }
        );
        cb = new JComboBox();
        cb.addItem("All");
        JButton jb = new JButton("˽�Ĵ���");
        JPanel pl = new JPanel(new BorderLayout());
        pl.add(cb);
        pl.add(jb, BorderLayout.WEST);
        JPanel p = new JPanel(new BorderLayout());
            p.add(pl, BorderLayout.WEST);
            p.add(tf);
            f.getContentPane().add(p, BorderLayout.SOUTH);
            f.getContentPane().add(sp);
            f.setVisible(true);

            GetMessageThread getMessageThread=new GetMessageThread(this);
            getMessageThread.start();
            showXXXIntochatRoom();

             /*
                ��ʾXXX����������
             */
            showXXXInChatRoom();
    }

    private void sendAll() {
        String url= "jdbc:oracle:thin:@localhost:1521:orcl";
        String username_db = "opts";
        String password_db= "opts1234";
        PreparedStatement pstmt=null;
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url, username_db, password_db);
            String sql = "SELECT username,ip,port FROM users WHERE status='online'";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String ip = rs.getString("IP");
                int port = rs.getInt("PORT");
                System.out.println(ip);
                System.out.println(port);
                byte[] ipB = new byte[4];
                String ips[] = ip.split("\\.");
                for (int i = 0; i < ips.length; i++) {
                    ipB[i] = (byte) Integer.parseInt(ips[i]);
                }
                if (!username.equals(name)) {
                    //String message = name + "������������";
                    String message=tf.getText();
                    byte[] m = message.getBytes();
                    DatagramPacket dp = new DatagramPacket(m, m.length);
                    dp.setAddress(InetAddress.getByAddress(ipB));
                    dp.setPort(port);
                    DatagramSocket ds = new DatagramSocket();
                    ds.send(dp);
                }
            }
        }catch(SQLException| UnknownException| SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void showXXXInChatRoom() {
        String url= "jdbc:oracle:thin:@localhost:1521:orcl";
        String username_db = "opts";
        String password_db= "opts1234";
        PreparedStatement pstmt=null;
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url, username_db, password_db);
            String sql = "SELECT username,ip,port FROM users WHERE status='online' AND username!=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String message = username + "����������";
                ta.append(message);
                cb.addItem(username);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void showXXXIntochatRoom() {
        String url= "jdbc:oracle:thin:@localhost:1521:orcl";
        String username_db = "opts";
        String password_db= "opts1234";
        PreparedStatement pstmt=null;
       Connection conn=null;
        try {
            conn = DriverManager.getConnection(url, username_db, password_db);
            String sql = "SELECT username,ip,port FROM users WHERE status='online'";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String ip = rs.getString("IP");
                int port = rs.getInt("PORT");
                System.out.println(ip);
                System.out.println(port);
                byte[] ipB = new byte[4];
                String ips[] = ip.split("\\.");
                for (int i = 0; i < ips.length; i++) {
                    ipB[i] = (byte) Integer.parseInt(ips[i]);
                }
                if (!username.equals(name)) {
                    String message = name + "������������";
                    byte[] m = message.getBytes();
                    DatagramPacket dp = new DatagramPacket(m, m.length);
                    dp.setAddress(InetAddress.getByAddress(ipB));
                    dp.setPort(port);
                    DatagramSocket ds = new DatagramSocket();
                    ds.send(dp);
                }
            }
        }catch(SQLException| UnknownException| SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}

