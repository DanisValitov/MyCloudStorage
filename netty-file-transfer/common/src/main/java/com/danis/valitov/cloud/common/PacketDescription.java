package com.danis.valitov.cloud.common;

public class PacketDescription extends  MyPacket{
    private long count;
    private String  fileName;

    public PacketDescription(long count, String fileName) {
        this.count = count;
        this.fileName = fileName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
