package com.example.seungmin1216.team.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    private Long id;
    private String mem_name;
    private String mem_mid;
    private String mem_pw;
    private String mem_myp;
    private String mem_email;
    private String mem_subp;
    private String mem_etc;
    private Long mem_age;
    private String mem_key;
}
