package com.milestone.plancus.Api.RequestForm;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ConfirmPlanForm {
    private Long headId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @Override
    public String toString() {
        return "{" +
                "\"headId\":\"" + headId + "\"" +
                ", \"startTime\":\"" + startTime + "\"" +
                ", \"endTime\":\"" + endTime + "\""+
                '}';
    }
}
