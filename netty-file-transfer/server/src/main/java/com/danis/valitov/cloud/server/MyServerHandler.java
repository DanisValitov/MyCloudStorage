package com.danis.valitov.cloud.server;

import com.danis.valitov.cloud.common.FilePacket;
import com.danis.valitov.cloud.common.PacketDescription;
import com.danis.valitov.cloud.common.PacketMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private List<FilePacket> list = new LinkedList<>();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
if(msg == null)return;

if (msg instanceof PacketMsg){
    PacketMsg packetMsg = (PacketMsg)msg;
    System.out.println(packetMsg.getMsg());
    ctx.writeAndFlush("Получено");
}

if (msg instanceof FilePacket){
    System.out.println("Получение файла...");
    FilePacket filePacket = (FilePacket)msg;
    String path = filePacket.getPath();
    list.add(filePacket);
}

if(msg instanceof PacketDescription){
    PacketDescription packetDescription = (PacketDescription)msg;
    int countOfPackets = packetDescription.getCount();
    String fileName = packetDescription.getFileName();
    writeFile(countOfPackets,fileName );
}

    }

    private void writeFile(int count, String name) {
        System.out.println("Кол-во пакетов: " + list.size());
        String path = "C:\\Users\\12\\Desktop\\проекты\\netty-file-transfer\\server_storage\\" + name;
        File file = new File(path);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(name)));
            for (FilePacket fp: list
                 ) {
                bos.write(fp.getData());
                System.out.println(list.indexOf(fp)+"-й пакет записан в файл");
            }
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
