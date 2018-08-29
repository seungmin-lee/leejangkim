package com.example.seungmin1216.team.event;

public class BookmarkEvent {

    private Integer kind;
    private String start;
    private String end;

    public BookmarkEvent(){

    }


    public BookmarkEvent(Integer kind, String start, String end) {
        this.kind = kind;
        this.start = start;
        this.end = end;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
