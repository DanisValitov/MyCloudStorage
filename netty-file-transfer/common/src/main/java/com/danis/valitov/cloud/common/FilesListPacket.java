package com.danis.valitov.cloud.common;

import java.util.List;

public class FilesListPacket extends MyPacket {
    private List<String> list;

    public FilesListPacket(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}
