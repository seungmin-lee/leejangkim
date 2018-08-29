package com.example.seungmin1216.team.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MapAdrresName {

    private String addr_name;
    private String categoty_name;
    private String category_group_code;
    private String road_address_name;

}
