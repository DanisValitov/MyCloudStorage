package com.danis.valitov.cloud.server;

import com.danis.valitov.cloud.common.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
   private  long countOfPackets = 0;
   private long counter;
   private String name;
   private  File file;
   private BufferedOutputStream bos ;
   private String serverStorageRoot = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\server_storage\\";
   private String currentUserRoot;






    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
if(msg == null)return;

if (msg instanceof PacketMsg){
    PacketMsg packetMsg = (PacketMsg)msg;
    System.out.println(packetMsg.getMsg());
    ctx.writeAndFlush(new PacketMsg("gjke"));

}

if (msg instanceof FilePacket){
//    System.out.println("Получение файла...");
    FilePacket filePacket = (FilePacket)msg;
   bos.write(filePacket.getData());
   counter--;
    System.out.println(counter);
    if(counter == 0){
        bos.close();
        System.out.println("all done");
        counter = 0;
        ctx.writeAndFlush(new FilesListPacket(listOfFiles(currentUserRoot)));
    }
}

if(msg instanceof PacketDescription){

    PacketDescription packetDescription = (PacketDescription)msg;
    countOfPackets  = packetDescription.getCount();
    name = packetDescription.getFileName();
    file = new File(currentUserRoot +"\\" +name );
    System.out.println(file);
    counter = countOfPackets;
    System.out.println("awaiting for " + countOfPackets + " packets");
    bos = new BufferedOutputStream(new FileOutputStream(file));
}

// ИСПРАВИТЬ!!!!!!!!!


if(msg instanceof RequestPacket){
    System.out.println("Запрос получен");
    RequestPacket requestPacket = (RequestPacket)msg;
    switch (requestPacket.getRequest()){
        case REFRESH:
            System.out.println("refresh request");
            String owner = requestPacket.getDecription();
            System.out.println("Owner: " + owner);
            currentUserRoot = serverStorageRoot + owner;
            List<String>list = listOfFiles(currentUserRoot);
            for (String qw: list
                 ) {
                System.out.println(qw);
            }
            ctx.writeAndFlush(new FilesListPacket(listOfFiles(currentUserRoot)));
        break;
        case FILE:
            String from = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\server_storage" + requestPacket.getDecription();
            System.out.println("qweqweqweqwe");
            System.out.println(from);
            sendNewFile(from,ctx);
            break;

        case NEW_DIRRECTORY:
            String newfolder = serverStorageRoot + requestPacket.getDecription();
            System.out.println(newfolder);
            Files.createDirectories(Paths.get(newfolder));

            System.out.println("new folderwas created!!!!");

            break;
    }
}

    }


    public  List<String> listOfFiles(String dirrect){
       // String path = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\server_storage";
        File dir = new File(dirrect);
        File [] arrFile = dir.listFiles();
        List<String> filesNames = new ArrayList<>();
        for (File file: arrFile
        ) {
            filesNames.add(file.getName());
        }
        return filesNames;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.toString());
        ctx.close();
    }

    public static void sendNewFile(String text,ChannelHandlerContext ctx) {
        File newFile = new File(text);
        long countOfPackets = newFile.length()/(1024*1024l) + 1;
        System.out.println(newFile.getName());
        System.out.println(newFile);

        System.out.println(countOfPackets);

        sendDescription(countOfPackets,newFile.getName(),ctx);
        fileSend(text,ctx);

    }

    public  static  void  fileSend(String name, ChannelHandlerContext ctx){
        File file = new File(name);
        MyPacket packet;
        final int SIZE = 1024*1024;
        byte [] b = new byte[SIZE];
        int counts = 0;



        BufferedInputStream bis;
        System.out.println(file.getName());
        System.out.println(file.length());

        if(file.exists()){
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                packet = new FilePacket(file.getName());
                while (bis.available() > 0){
                    if(bis.available() < SIZE) b = new byte[bis.available()];
                    bis.read(b);
                    ((FilePacket) packet).setData(b);
                    ctx.writeAndFlush(packet);
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

    public static void   sendDescription(long count, String name,ChannelHandlerContext ctx ) {
        MyPacket packet = new PacketDescription(count,name);

            ctx.writeAndFlush(packet);

    }
}
