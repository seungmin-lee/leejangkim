package com.example.seungmin1216.team.event;

public class BusBookmarkEvent {
    private String station;

    public BusBookmarkEvent() {

    }

    public BusBookmarkEvent(String station) {
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
