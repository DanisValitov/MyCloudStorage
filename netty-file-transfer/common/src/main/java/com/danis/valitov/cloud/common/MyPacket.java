package com.danis.valitov.cloud.common;

import java.io.Serializable;

public abstract class MyPacket implements Serializable {
    private  String Name;
    private String uniqueID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
