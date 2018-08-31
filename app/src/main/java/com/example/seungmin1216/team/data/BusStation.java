package com.example.seungmin1216.team.data;

public class BusStation {

    private String busRouteNm;
    private String seq;
    private String station;
    private String stationNm;
    private String stationNo;
    private String beginTm;
    private String lastTm;
    private String trnstnid;
    private String gpsX;
    private String gpsY;

    public BusStation( String busRouteNm, String seq, String station, String stationNm, String stationNo, String beginTm, String lastTm, String trnstnid,String gpsX,String gpsY) {

        this.busRouteNm = busRouteNm;
        this.seq = seq;
        this.station = station;
        this.stationNm = stationNm;
        this.stationNo = stationNo;
        this.beginTm = beginTm;
        this.lastTm = lastTm;
        this.trnstnid = trnstnid;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
    }


    public String getBusRouteNm() {
        return busRouteNm;
    }

    public void setBusRouteNm(String busRouteNm) {
        this.busRouteNm = busRouteNm;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationNm() {
        return stationNm;
    }

    public void setStationNm(String stationNm) {
        this.stationNm = stationNm;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getBeginTm() {
        return beginTm;
    }

    public void setBeginTm(String beginTm) {
        this.beginTm = beginTm;
    }

    public String getLastTm() {
        return lastTm;
    }

    public void setLastTm(String lastTm) {
        this.lastTm = lastTm;
    }

    public String getTrnstnid() {
        return trnstnid;
    }

    public void setTrnstnid(String trnstnid) {
        this.trnstnid = trnstnid;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setPosX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }
}
