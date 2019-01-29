package com.danis.valitov.cloud.client;

import com.danis.valitov.cloud.common.FilePacket;
import com.danis.valitov.cloud.common.MyPacket;
import com.danis.valitov.cloud.common.PacketDescription;
import com.danis.valitov.cloud.common.PacketMsg;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.*;
import java.net.Socket;

public class Network {
    private static Socket socket;
    private static ObjectEncoderOutputStream out;
    private static ObjectDecoderInputStream in;

    private static final int MAX_OBJ_SIZE = 100 * 1024 * 1024;

    public static void start() {
        try {
            socket = new Socket("localhost", 8189);
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            in = new ObjectDecoderInputStream(socket.getInputStream(), MAX_OBJ_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean sendMsg(String msg) {
        MyPacket packet = new PacketMsg(msg);
        try {
            out.writeObject(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendDescription(int count, String name) {
        MyPacket packet = new PacketDescription(count,name);
        try {
            out.writeObject(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

public  static  void  fileSend(String  path){
        byte [] b;
        int counts = 0;
        MyPacket packet;
        File file = new File(path);
        final int SIZE = 1024*1024;
    System.out.println(file.getName());
    System.out.println(file.length());
    BufferedInputStream bis;
    if(file.exists()){
        try {
            bis = new BufferedInputStream(new FileInputStream(file));

            while (bis.available() > 0){
                if(bis.available() < SIZE){
                    b= new byte[bis.available()];
                } else b = new byte[SIZE];
                packet = new FilePacket(file.getName());
                bis.read(b);
                ((FilePacket) packet).setData(b);
                out.writeObject(packet);
                counts++;
            }
            sendDescription(counts,file.getName());

            bis.close();
            System.out.println("number of packets: " + counts);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else System.out.println("Файл не найден");
}


    public static MyPacket readObject() throws ClassNotFoundException, IOException {
        Object obj = in.readObject();
        return (MyPacket) obj;
    }
}
