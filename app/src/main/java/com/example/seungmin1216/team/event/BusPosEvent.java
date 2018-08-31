package com.example.seungmin1216.team.event;

public class BusPosEvent {
    private String id;
    private String num;
    private String type;

    public BusPosEvent(String id, String num, String type) {
        this.id = id;
        this.num = num;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


