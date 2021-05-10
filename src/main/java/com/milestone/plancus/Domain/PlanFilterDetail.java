package com.milestone.plancus.Domain;

import lombok.Getter;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name ="pf_detail")
@Getter
public class PlanFilterDetail {
    public PlanFilterDetail() {
    }

    public PlanFilterDetail(PlanFilterHead headId, Member member, Member host, LocalDateTime useStartTime, LocalDateTime useEndTime, LocalDateTime createByDate, Boolean isRead) {
        this.headId = headId;
        this.member = member;
        this.host = host;
        this.useStartTime = useStartTime;
        this.useEndTime = useEndTime;
        this.createByDate = createByDate;
        this.isRead = isRead;
    }

    @Id
    @Column(name = "PFD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PFH_ID")
    private PlanFilterHead headId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PFD_MEM_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PFD_HOST_ID")
    private Member host;

    @Column(name = "PFD_STIME",  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime useStartTime;

    @Column(name = "PFD_ETIME",  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime useEndTime;

    @Column(name = "PFD_CREATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "PFD_UPDATEBYDATE")
    private LocalDateTime updateByDate;

    @Column(name = "PFD_ISREAD")
    private Boolean isRead;




}
