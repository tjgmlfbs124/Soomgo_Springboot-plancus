package com.milestone.plancus.Api.Form;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestPlanForm {
    private String title;
    private String color;
    private LocalDateTime limitedDays;
    private List<String> memberIds;
}
