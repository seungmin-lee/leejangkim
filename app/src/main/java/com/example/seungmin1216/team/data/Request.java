package com.example.seungmin1216.team.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Request {
    private Long id;
    private Long li_kind;//0은 신청 1은 승인
    private String li_station;
    private Long li_year;
    private Long li_month;
    private Long li_day;
    private Long li_hour;
    private Long li_minute;
    private String li_post;
    private Long li_appr;
    private Long mem_id;//로그인 데이터 고유 아이디
    private String li_otherSta;
}
