package com.danis.valitov.cloud.common;

public class PacketDescription extends  MyPacket{
    private int count;
    private String  fileName;

    public PacketDescription(int count, String fileName) {
        this.count = count;
        this.fileName = fileName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
