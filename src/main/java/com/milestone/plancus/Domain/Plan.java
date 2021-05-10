package com.milestone.plancus.Domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Plan {
    public Plan() {
    }

    public Plan(String title, String color, String local, Member member, Member host, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.color = color;
        this.local = local;
        this.member = member;
        this.host = host;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PLAN_TITLE")
    private String title;

    @Column(name = "PLAN_COLOR")
    private String color;

    @Column(name = "PLAN_LOCAL")
    private String local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEM_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_ID")
    private Member host;

    @Column(name = "PLAN_STIME")
    private LocalDateTime startTime;

    @Column(name = "PLAN_ETIME")
    private LocalDateTime endTime;

    @Column(name = "PLAN_CREATEBYDATE",insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    @Column(name = "PLAN_UPDATEBYDATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateByDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_FILTER")
    private PlanFilter filter;

    public String toJsonString(){
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"local\":\"" + local + "\""+
                ", \"member\":" + member.toJsonString()  + "\""+
                ", \"startTime\":\"" + startTime  + "\""+
                ", \"endTime\":\"" + endTime  + "\""+
                ", \"filter\":\"" + filter  + "\""+
                ", \"createByDate\":\"" + createByDate  + "\""+
                ", \"updateByDate\":\"" + updateByDate  + "\""+
                '}';
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", title='" + title + '\"' +
                ", color='" + color + '\"' +
                ", local='" + local + '\"' +
                ", member=" + member.toString() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createByDate=" + createByDate +
                ", updateByDate=" + updateByDate +
                ", filter=" + filter +
                '}';
    }
}
