package com.milestone.plancus.Domain;

import com.milestone.plancus.Api.DTO.PlanDTO;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Plan {
    public Plan() {
    }

    public Plan(String title, String color, Member member, Member host, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.color = color;
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

    @OneToOne(mappedBy = "plan")
    private Map map;

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

    @OneToMany(mappedBy = "plan")
    private List<TodoList> todoList = new ArrayList<>();

    @Column(name = "PLAN_CREATEBYDATE",insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createByDate;

    public PlanDTO toDTO(){
        return new PlanDTO(
                this.getTitle(),
                this.getColor(),
                this.getMap(),
                this.getMember().toDto(),
                this.getTodoList(),
                this.getStartTime(),
                this.getEndTime()
        );
    }

    public String toJsonString(){
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":\"" + map + "\""+
                ", \"host\":" + host.toJson()  + "\""+
                ", \"member\":" + member.toJson()  + "\""+
                ", \"todoList\":" + todoList  + "\""+
                ", \"startTime\":\"" + startTime  + "\""+
                ", \"endTime\":\"" + endTime  + "\""+
                ", \"createByDate\":\"" + createByDate  + "\""+
                '}';
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"map\":" + map +
                ", \"host\":" + host  +
                ", \"member\":" + member +
                ", \"todoList\":" + todoList  +
                ", \"startTime\":\"" + startTime  + "\""+
                ", \"endTime\":\"" + endTime  + "\""+
                ", \"createByDate\":\"" + createByDate  + "\""+
                '}';
    }
}
