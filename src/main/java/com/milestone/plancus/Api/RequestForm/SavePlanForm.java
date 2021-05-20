package com.milestone.plancus.Api.RequestForm;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SavePlanForm {
    private String planTitle;
    private String color;
    private String local;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime filterStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime filterEndTime;

    private String hostId;

    private List<String> joinMemberIds = new ArrayList<>();

    private List<Long> availableDaysIndexs = new ArrayList<>();

    private List<String> positiveMemberIds = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate selectDate;
//    private Attendance result;

    public String toJsonString(){
        return "{" +
                "\"planTitle\":\"" + planTitle  + "\""+
                ", \"color\":\"" + color  + "\""+
                ", \"local\":\"" + local  + "\""+
                ", \"startDate\":\"" + startDate.toString()  + "\""+
                ", \"endDate\":\"" + endDate.toString()  + "\""+
                ", \"filterStartTime\":\"" + filterStartTime.toString()  + "\""+
                ", \"filterEndTime\":\"" + filterEndTime.toString()  + "\""+
                ", \"host\":\"" + hostId + "\""+
                ", \"joinMembers\":" + joinMemberIds.toString() +
                ", \"availableDaysIndexs\":" + availableDaysIndexs.toString() +
                ", \"positiveMemberIds\":" + positiveMemberIds.toString() +
                ", \"selectDate\":\"" + selectDate.toString()  + "\""+
                "}";
    }
}
