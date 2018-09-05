package com.example.seungmin1216.team.Map;

import com.example.seungmin1216.team.data.Documents;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private ArrayList<Documents> documents;

}
