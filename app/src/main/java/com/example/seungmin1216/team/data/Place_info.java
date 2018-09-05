package com.example.seungmin1216.team.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Place_info {
    private String place_name;
    private String place_addr;
    private String place_num;
    private String place_cate;


}
