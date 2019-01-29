package com.danis.valitov.cloud.common;

public class PacketMsg extends MyPacket {
    private String msg;

    public PacketMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
