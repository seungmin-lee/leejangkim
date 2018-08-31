package com.example.seungmin1216.team.data;

public class BusPos {
    private String sectOrd;
    private String bus_gpsX;
    private String bus_gpsY;
    private String bus_plainNo;
    private String busType;

    public BusPos(String sectOrd, String bus_gpsX, String bus_gpsY, String bus_plainNo, String busType) {
        this.sectOrd = sectOrd;
        this.bus_gpsX = bus_gpsX;
        this.bus_gpsY = bus_gpsY;
        this.bus_plainNo = bus_plainNo;
        this.busType = busType;
    }


    public String getSectOrd() {
        return sectOrd;
    }

    public void setSectOrd(String sectOrd) {
        this.sectOrd = sectOrd;
    }

    public String getBus_gpsX() {
        return bus_gpsX;
    }

    public void setBus_gpsX(String bus_gpsX) {
        this.bus_gpsX = bus_gpsX;
    }

    public String getBus_gpsY() {
        return bus_gpsY;
    }

    public void setBus_gpsY(String bus_gpsY) {
        this.bus_gpsY = bus_gpsY;
    }

    public String getBus_plainNo() {
        return bus_plainNo;
    }

    public void setBus_plainNo(String bus_plainNo) {
        this.bus_plainNo = bus_plainNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }
}
