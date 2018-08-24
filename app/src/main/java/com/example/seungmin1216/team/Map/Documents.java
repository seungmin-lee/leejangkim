package com.example.seungmin1216.team.Map;

import java.util.ArrayList;

public class Documents {
    private String address_name;
    private String y;
    private String x;
    private String address_type;
    private ArrayList<Address> address;
    private ArrayList<Road_Address> road_address;

    public Documents(String address_name, String y, String x, String address_type, ArrayList<Address> address, ArrayList<Road_Address> road_address) {
        this.address_name = address_name;
        this.y = y;
        this.x = x;
        this.address_type = address_type;
        this.address = address;
        this.road_address = road_address;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public ArrayList<Address> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<Address> address) {
        this.address = address;
    }

    public ArrayList<Road_Address> getRoad_address() {
        return road_address;
    }

    public void setRoad_address(ArrayList<Road_Address> road_address) {
        this.road_address = road_address;
    }
}
