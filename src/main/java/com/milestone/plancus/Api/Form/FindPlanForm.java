package com.milestone.plancus.Api.Form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FindPlanForm {
    private String title;
    private String color;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime limitedDays;

    private List<String> memberIds;
    private String local;
    private String availableStartTime;
    private String availableEndTime;
    private List<Long> availableDaysIndex;

    public FindPlanForm() {
    }

    public FindPlanForm(String title, String color, LocalDateTime limitedDays, List<String> memberIds, String local, String availableStartTime, String availableEndTime, List<Long> availableDaysIndex) {
        this.title = title;
        this.color = color;
        this.limitedDays = limitedDays;
        this.memberIds = memberIds;
        this.local = local;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.availableDaysIndex = availableDaysIndex;
    }

    @Override
    public String toString() {

        return "{" +
                "\"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"local\":\"" + local + "\""+
                ", \"limitedDays\":\"" + limitedDays.toString()  + "\""+
                ", \"members\":" + memberIds  + ""+
                ", \"availableStartTime\":\"" + availableStartTime  + "\""+
                ", \"availableEndTime\":\"" + availableEndTime  + "\""+
                ", \"availableDaysIndex\":" + availableDaysIndex  +
                '}';

    }
}
