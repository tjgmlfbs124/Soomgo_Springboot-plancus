package com.milestone.plancus.Domain;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "pf_head")
@Getter
public class PlanFilterHead {

    public PlanFilterHead() {
    }

    public PlanFilterHead(String local, LocalDateTime limitedDate, int useTimes, LocalDateTime createByDate, LocalDateTime updateByDate, FilterType state) {
        this.local = local;
        this.limitedDate = limitedDate;
        this.useTimes = useTimes;
        this.createByDate = createByDate;
        this.updateByDate = updateByDate;
        this.state = state;
    }

    @Id
    @Column(name = "PFH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="PFH_LOCAL")
    private String local;

    @Column(name = "PFH_LIMITDATE")
    private LocalDateTime limitedDate;

    @Column(name= "PFH_TIME")
    private int useTimes;

    @Column(name = "PFH_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "PFH_UPDATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;

    @Column(name = "PFH_STATE")
    @Enumerated(EnumType.STRING)
    private FilterType state;



}
