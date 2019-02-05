package com.danis.valitov.cloud.client;

import com.danis.valitov.cloud.common.*;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    public static boolean sendDescription(long count, String name) {
        MyPacket packet = new PacketDescription(count,name);
        try {
            out.writeObject(packet);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

public  static  void  fileSend(String name){
        File file = new File(name);
    final int SIZE = 1024*1024;
        byte [] b = new byte[SIZE];
        int counts = 0;
        MyPacket packet;



    System.out.println(file.getName());
    System.out.println(file.length());
    BufferedInputStream bis;
    if(file.exists()){
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            packet = new FilePacket(file.getName());
            while (bis.available() > 0){
               if(bis.available() < SIZE) b = new byte[bis.available()];
               bis.read(b);
                ((FilePacket) packet).setData(b);
                out.writeObject(packet);
                counts++;
            }


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


    public static void sendNewFile(String text) {
        File newFile = new File(text);
        System.out.println(newFile.getName());
        System.out.println(newFile);
        long countOfPackets = newFile.length()/(1024*1024l) + 1;
        System.out.println(countOfPackets);

            sendDescription(countOfPackets,newFile.getName());
            fileSend(text);

    }
//
//    public static void refreshRequest(String userName){
//        RequestPacket requestPacket = new RequestPacket(Request.REFRESH,userName);
//        try {
//            out.writeObject(requestPacket);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void request(Request request, String userName) {
        RequestPacket requestPacket = null;
        if(request.equals(Request.REFRESH))
         requestPacket = new RequestPacket(request,userName);

        if (request.equals(Request.NEW_DIRRECTORY))
            requestPacket = new RequestPacket(request,userName);

        if (request.equals(Request.FILE))
            requestPacket = new RequestPacket(request,userName);

        try {
            out.writeObject(requestPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
