package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterResult {

    public FilterResult() {
    }

    public FilterResult(String planTitle, String color, String local, LocalDate startDate, LocalDate endDate, LocalDateTime filterStartTime, LocalDateTime filterEndTime, List<Long> availableDaysIndexs) {
        this.planTitle = planTitle;
        this.color = color;
        this.local = local;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filterStartTime = filterStartTime;
        this.filterEndTime = filterEndTime;
        this.availableDaysIndexs = availableDaysIndexs;
    }

    private String planTitle;
    private String color;
    private String local;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDateTime filterStartTime;
    private LocalDateTime filterEndTime;

    private MemberDTO host;
    private List<MemberDTO> joinMembers = new ArrayList<>();

    private List<Long> availableDaysIndexs = new ArrayList<>();

    private List<Attendance> result = new ArrayList<>();

    public void addJoinMembers(MemberDTO memberDTO){
        if (!this.joinMembers.contains(memberDTO)){
            this.joinMembers.add(memberDTO);
        }
    }

    public void addResult(Attendance attendance){
        this.result.add(attendance);
    }

    public String toJsonString() {
        return "{" +
                "\"planTitle\":\"" + planTitle  + "\""+
                ", \"color\":\"" + color  + "\""+
                ", \"local\":\"" + local  + "\""+
                ", \"startDate\":\"" + startDate.toString()  + "\""+
                ", \"endDate\":\"" + endDate.toString()  + "\""+
                ", \"filterStartTime\":\"" + filterStartTime.toString()  + "\""+
                ", \"filterEndTime\":\"" + filterEndTime.toString()  + "\""+
                ", \"host\":" + host.toString() +
                ", \"joinMembers\":" + joinMembers.toString() +
                ", \"availableDaysIndexs\":" + availableDaysIndexs.toString() +
                ", \"result\":" + result.toString() +
                "}";
    }

}
