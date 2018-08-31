package com.example.seungmin1216.team.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Bus {

    private String busRouteId;
    private String busRouteNum;
    private String busRouteType;
    private String busStStaionName;
    private String busEndStationName;

}
