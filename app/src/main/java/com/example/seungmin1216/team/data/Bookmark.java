package com.example.seungmin1216.team.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bookmark {
    private Long id;
    private Long book_kind;
    private String book_start;
    private String book_end;
    private Long mem_id;
}
