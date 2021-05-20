package com.milestone.plancus.Api.ResponseForm;

import com.milestone.plancus.Api.DTO.PlanTagDto;
import lombok.Data;

@Data
public class TagsListResponse {
    private int totalSeconds = 0;

    private PlanTagDto main_business;
    private PlanTagDto sub_business;
    private PlanTagDto develop;
    private PlanTagDto family;
    private PlanTagDto friend;
    private PlanTagDto network;
    private PlanTagDto etc;

    public void addTotalSecond(long second){
        this.totalSeconds += second;

    }


}
