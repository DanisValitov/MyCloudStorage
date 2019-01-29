package com.danis.valitov.cloud.common;

public class FilePacket extends  MyPacket {
    private  byte[] data;
    private  String name;

    public FilePacket(String path) {
        this.name = path;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getPath() {
        return name;
    }

    public void setPath(String path) {
        this.name = path;
    }
}

