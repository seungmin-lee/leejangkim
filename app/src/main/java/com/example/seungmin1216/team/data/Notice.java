package com.example.seungmin1216.team.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Notice {
    private Long id;
    private String notice_title;
    private String notice_contents;
    private String notice_date;

}
