package com.example.seungmin1216.team.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequesetData {

    int type;//버스 , 지하철 , 택시
    String origin;//출발
    String destination;//도착
    String memo;
    int check;
    int year;
    int month;
    int day;
    int hour;//시간
    int minute;
}
