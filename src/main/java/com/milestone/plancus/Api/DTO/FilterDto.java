package com.milestone.plancus.Api.DTO;

import com.milestone.plancus.Domain.FilterType;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.PlanFilterAvailableTimes;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterDto {
    // Head
    private Long headId;
    private String title;
    private String color;
    private String local;
    private Member host;
    private LocalDateTime limitedDate;
    private String useTimes;
    private FilterType state;
    private LocalDateTime createByDate;

    // Detail
    private Member member;
    private Boolean isRead;
    private Boolean isJoin;
    private List<PlanFilterAvailableTimes> availableTimes = new ArrayList<>();

    public FilterDto(Long headId, String title, String color, Member host, FilterType state, LocalDateTime createByDate, Boolean isRead, Boolean isJoin) {
        this.headId = headId;
        this.title = title;
        this.color = color;
        this.host = host;
        this.state = state;
        this.createByDate = createByDate;
        this.isRead = isRead;
        this.isJoin = isJoin;
    }

    public FilterDto(Long headId, String title, String color, String local, Member host, LocalDateTime limitedDate, String useTimes, FilterType state, Member member, Boolean isRead, LocalDateTime createByDate) {
        this.headId = headId;
        this.title = title;
        this.color = color;
        this.local = local;
        this.host = host;
        this.limitedDate = limitedDate;
        this.useTimes = useTimes;
        this.state = state;
        this.member = member;
        this.isRead = isRead;
        this.createByDate = createByDate;
    }

    public void addAvaiableTimes(PlanFilterAvailableTimes times){
        this.availableTimes.add(times);
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
                "headId=" + headId +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", local='" + local + '\'' +
                ", host=" + host +
                ", limitedDate=" + limitedDate +
                ", useTimes='" + useTimes + '\'' +
                ", state=" + state +
                ", member=" + member +
                ", isRead=" + isRead +
                ", createByDate=" + createByDate +
                ", availableTimes=" + availableTimes +
                '}';
    }

    public String toJson(){
        return "{" +
                "\"headId\":\"" + headId + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"color\":\"" + color + "\"" +
                ", \"local\":\"" + local + "\"" +
                ", \"host\":" + host +
                ", \"limitedDate\":\"" + limitedDate + "\"" +
                ", \"useTimes\":\"" + useTimes + "\"" +
                ", \"state\":\"" + state + "\"" +
                ", \"member\":" + member +
                ", \"isRead\":" + isRead +
                ", \"createByDate\":\"" + createByDate + "\"" +
                ", \"availableTimes\":" + availableTimes.toString()  +
                '}';
    }
}
