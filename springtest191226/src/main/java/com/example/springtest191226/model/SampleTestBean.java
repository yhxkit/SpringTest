package com.example.springtest191226.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class SampleTestBean {

    public int idx;
    public String name;

    public SampleTestBean(int idx, String name){
        this.idx = idx;
        this.name = name;
    }

}
