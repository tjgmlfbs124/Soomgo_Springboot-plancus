package com.milestone.plancus.Api.DTO;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanDTO {
    private Long id;
    private String title;
    private String color;
    private String local;
    private MemberDTO host;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PlanDTO(Long id, String title, String color, String local, MemberDTO host, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.local = local;
        this.host = host;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
