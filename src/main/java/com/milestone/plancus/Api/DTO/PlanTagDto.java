package com.milestone.plancus.Api.DTO;

import lombok.Data;

@Data
public class PlanTagDto {
    public PlanTagDto(String tag) {
        this.tag = tag;
    }

    private String tag;
    private int second = 0;

    public void addSecond(long second){
        this.second += second;
    }
}
