package com.example.seungmin1216.team.event;

public class SubwayBookmarkEvent {
    private String start;
    private String end;

    public SubwayBookmarkEvent(){

    }

    public SubwayBookmarkEvent(String start, String end) {
        this.start = start;
        this.end = end;
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
