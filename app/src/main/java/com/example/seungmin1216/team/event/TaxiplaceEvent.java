package com.example.seungmin1216.team.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxiplaceEvent {
    private String addr;
    private String place_name;
}
