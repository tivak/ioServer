package com.top2fox.test.ioServer.common.model;

public class Entity {
    private int intParam1;
    private int intParam2;
    private String uid;

    public int getIntParam1() {
        return intParam1;
    }

    public Entity setIntParam1(int intParam1) {
        this.intParam1 = intParam1;
        return this;
    }

    public int getIntParam2() {
        return intParam2;
    }

    public Entity setIntParam2(int intParam2) {
        this.intParam2 = intParam2;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Entity setUid(String uid) {
        this.uid = uid;
        return this;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "intParam1=" + intParam1 + ", " +
                "intParam2=" + intParam2 + ", " +
                "uid=" + uid +
            "}";
    }
}
