package com.milestone.plancus.Domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name ="MEMBER_UTIMES")
@Getter
public class PlanFilterAvailableTimes {
    public PlanFilterAvailableTimes() {
    }

    public PlanFilterAvailableTimes(PlanFilterDetail detail, String startTime, String endTime) {
        this.detail = detail;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEM_UTIMES_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEM_UTIMES_DETAIL")
    private PlanFilterDetail detail;

    @Column(name = "MEM_UTIMES_STIME")
    private String startTime;

    @Column(name = "MEM_UTIMES_ETIME")
    private String endTime;

    @Column(name = "MEM_UTIMES_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;
}
