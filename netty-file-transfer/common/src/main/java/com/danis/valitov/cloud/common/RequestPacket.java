package com.danis.valitov.cloud.common;

public class RequestPacket extends  MyPacket {
    private Request request;
    private String decription;

    public RequestPacket(Request request) {
        this.request = request;
    }

    public RequestPacket(Request request, String decription) {
        this.request = request;
        this.decription = decription;
    }

    public Request getRequest() {
        return request;
    }

    public String getDecription() {
        return decription;
    }
}
